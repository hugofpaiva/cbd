from os import write
from neo4j import GraphDatabase
import csv


class ExC:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        self.queries_names = ["1. Listar todas as armas",
                              "2. Listar todos os ataques considerados suicidas",
                              "3. Listar os 5 tipos de ataques mais comuns",
                              "4. Listar as armas mais usadas em ataques feitos nos 'United States'",
                              "5. Listar os ataques com mortes superiores a 50 com o tipo 'Unknown'",
                              "6. Quais os 5 ataques mais mortíferos?",
                              "7. Qual o total de ataques com mortes por cada país, ordenados por ordem decrescente?",
                              "8. Qual o ano com mais mortes registadas?",
                              "9. Quantos ataques sem mortes ocorreram em países começados por 'P'",
                              "10. Listar por ordem decrescente a mortalidade das armas"]
        self.queries = ["MATCH (g:Arma) RETURN g.id, g.nome",
                        "MATCH (a:Ataque{suicidio:1}) RETURN a.id, a.ano, a.mes, a.dia, a.suicidio",
                        "MATCH (a:Ataque)-[r:TIPO]->(t:TipoAtaque) RETURN t.nome, count(r) as tipoCount ORDER BY tipoCount DESC LIMIT 5",
                        "MATCH (a:Ataque)-[r:ATACOU_O_ALVO]->(aa:AlvoAtaque{nome: 'United States'}), (a)-[r2:UTILIZOU_A_ARMA]->(g:Arma) RETURN g.nome, count(r2) as gunCount ORDER BY gunCount DESC",
                        "MATCH (a:Ataque)-[r:TIPO]->(t:TipoAtaque{nome: 'Unknown'}) WHERE a.nmortes > 50 RETURN a.id, a.ano, a.mes, a.dia, a.nmortes",
                        "MATCH (a:Ataque) RETURN a.id, a.ano, a.mes, a.dia, a.nmortes ORDER BY a.nmortes DESC LIMIT 5",
                        "MATCH (a:Ataque)-[r:ATACOU_O_ALVO]->(aa:AlvoAtaque) WHERE a.nmortes > 0 RETURN aa.nome, sum(a.nmortes) as mortes ORDER BY mortes DESC",
                        "MATCH (a:Ataque) RETURN a.ano, sum(a.nmortes) as mortes ORDER BY mortes DESC LIMIT 1",
                        "MATCH (a:Ataque)-[r:ATACOU_O_ALVO]->(aa:AlvoAtaque) WHERE aa.nome STARTS WITH 'P' AND a.nmortes=0 RETURN a.id, a.ano, a.mes, a.dia, a.nmortes, aa.nome as Alvo",
                        "MATCH (a:Ataque)-[r:UTILIZOU_A_ARMA]->(g:Arma) RETURN g.nome, sum(a.nmortes) as mortes ORDER BY mortes DESC"]

    def insert(self):
        self.driver.session().run(
            "LOAD CSV WITH HEADERS FROM 'file:///globalterrorism_new.csv' AS row MERGE (a:Ataque {id: row.AttackID, ano: toInteger(row.Year), mes: toInteger(row.Month), dia: toInteger(row.Day), suicidio: toInteger(row.suicide), nmortes: toInteger(row.nKills)}) MERGE (t:TipoAtaque {id: row.AttackTypeID, nome: row.AttackTypeName}) MERGE (aa:AlvoAtaque {id: row.TargetCountryID, nome: row.TargetCountryName}) MERGE (g:Arma {id: row.WeaponID, nome: row.WeaponName}) MERGE (a)-[r1:ATACOU_O_ALVO]->(aa) MERGE (a)-[r2:UTILIZOU_A_ARMA]->(g) MERGE (a)-[r3:TIPO]->(t);")

    def queriesAndSave(self):
        f = open("CBD_L44c_output.txt", "a")
        for idx, q in enumerate(self.queries):
            result = self.driver.session().run(q)
            f.write("\n\n"+self.queries_names[idx]+"\n\n")
            f.write(q+"\n\n---")

            results=[r for r in result.data()]

            for k in results[0].keys():
                f.write(f" {k}        ")
            f.write("---")
            for r in results[:5]:
                f.write("\n")
                for v in r.values():
                    f.write(f" {str(v)}        ")

            if(len(results)>5):
                f.write("\n...")

        f.close()
            

    def close(self):
        self.driver.close()


if __name__ == "__main__":
    try:
        bd = ExC("bolt://localhost:7687", "neo4j", "testdb")
        bd.insert()
        bd.queriesAndSave()
        print("Connected to database!")
        bd.close()
    except Exception as e:
        print("There was an error!", e)
