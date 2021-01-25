from neo4j import GraphDatabase

class Neo4jTest:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()


if __name__ == "__main__":
    try:
        bd = Neo4jTest("bolt://localhost:7687", "neo4j", "testdb")
        print("Connected to database!")
        bd.close()
    except:
        print("There was an error!")