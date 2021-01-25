
**Source:** https://www.kaggle.com/START-UMD/gtd

Filtrou-se o dataset para conter apenas dados posteriores a 2000 mantendo as seguinte colunas

**Columns:**
- AttackID
- Year
- Month
- Day
- CountryID
- CountryName
- RegionID
- RegionName
- CityName
- latitude
- longitude
- success (1- yes, 0- no) 
- suicide (1- yes, 0 - no)
- AttackTypeID
- AttackTypeName
- TargetTypeID
- TargetTypeName
- TargetCountryID
- TargetCountryName
- WeaponID
- WeaponName
- nKills

**Entidades:** 

    - Ataque
        - id
        - ano
        - mes
        - dia
        - suicidio
        - nmortes
    - TipoAtaque
        - id
        - nome
    - AlvoAtaque
        - id
        - nome
    - Arma
        - id
        - nome

**Relações:** 

    - ATACOU_O_ALVO
    - UTILIZOU_A_ARMA
    - TIPO


**Queries**

1. Listar todas as armas
2. Listar todos os ataques considerados suicidas
3. Listar os 5 tipos de ataques mais comuns
4. Listar as armas mais usadas em ataques feitos nos 'United States'
5. Listar os ataques com mortes superiores a 50 com o tipo 'Unknown'
6. Quais os 5 ataques mais mortíferos?
7. Qual o total de ataques bem sucedidos por cada país, ordenados por ordem decrescente?
8. Qual o ano com mais mortes registadas?
9. Quantos ataques mal sucedidos ocorreram em países começados por "P"
10. Listar por ordem decrescente a mortalidade das armas

## Executar

```
$ python3 -m venv venv
$ source venv/bin/activate
$ pip install -r requirements.txt
$ python3 exc.py
```
