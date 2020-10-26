# Instalação

[https://redis.io/](https://redis.io/)

### Executar

```bash
redis-server
```

### Redis Command Line

```bash
redis-cli
```

# Tipos de dados

### KEY

Redis keys are binary safe -any binary stream can be used as a key- although the most common (and recommended) stream to use is a string key, like "Person", other file formats and binary streams like images, mp3, or other file formats, can be used. Using different data types can impact the performance and stability of REDIS.

É interessante usar o nome das chaves como uma determinada estrutura para nos auxiliar até por exemplo a fazer SCAN e de pois com MATCH. Ex: Book:1, Book:1:comment, etc.

### STRING

In Redis, a string is not merely alphanumeric characters as strings are normally understood to be in higher-level programming languages, but are serialized characters in C. Integers are stored in Redis as a string.

### LIST

Lists in Redis are ordered collections of Redis strings that allows for duplicates values. Because Redis lists are implemented as linked-lists, adding an item to the front of the list with LPUSH or to the end of the list with RPUSH are relatively inexpensive operations that are performed in constant time of O(1).

### HASH

Hash data structure maps one or more fields to corresponding value pairs. In Redis, all hash values must be Redis strings with unique field names.

### SET

A set is a collection of string values where uniqueness of each member is guaranteed but does not offer ordering of members. Redis sets also implement union, intersection, and difference set semantics along with the ability to store the results of those set operations as a new Redis.

### SORTED SET

A sorted set combines characteristics of both Redis lists and sets. Like a Redis list, a sorted set's values are ordered and like a set, each value is assured to be unique. The flexibility of a sorted set allows for multiple types of access patterns depending on the needs of the application.

### Bit Array ou Bitmap

Redis bitstrings is a very memory efficient data structures in Redis. In a bitstring, 8 bits are stored per byte, with the first bit at position 0 being the significant one that is either set to either 0 or 1. The maximum size for Redis bitstrings is 512 MB.

### HyperLogLogs

Similar a Sorted Set, Redis data type is a probabilistic data structure that provides an estimated count of unique items in a collection.

# Comandos

**SCAN ->** iterates the set of keys in the currently selected Redis database.

```jsx
127.0.0.1:6379> scan 0
1) "0"
2) (empty array)

**Retorna 0 pq é o nosso cursor e se ainda houvesse chaves, tinhamos de fazer scan # (um número em vez de 0)
para mostrar os próximos 10**

127.0.0.1:6379> scan 0 MATCH "ol*"
1) "0"
2) (empty array)

Tenta encontrar chaves que comecem com ol
```

Outro exemplo:

```jsx
Now let’s use the SCAN command with an argument of 0:

1
2
3
4
5
6
7
8
9
10
11
12
13
127.0.0.1:6379> SCAN 0
1) "22"
2)  1) "key2"
    2) "key3"
    3) "key4"
    4) "key17"
    5) "key1"
    6) "key16"
    7) "key5"
    8) "key11"
    9) "key14"
   10) "key15"
127.0.0.1:6379>
First it returned “22” which is our cursor. The cursor keeps track of how where we are at in the scan.

The second thing it returned was 10 keys. It only returned 10 because by default it doesn’t return all your keys, by default it only returns 10.

So how do we get the rest of the keys? You use the cursor it returned (“22”) and run another SCAN command passing it in as the argument:

1
2
3
4
5
6
7
8
9
10
11
12
13
127.0.0.1:6379> SCAN 22
1) "0"
2)  1) "key9"
    2) "key19"
    3) "key12"
    4) "key18"
    5) "key8"
    6) "key20"
    7) "key6"
    8) "key13"
    9) "key10"
   10) "key7"
127.0.0.1:6379>
The cursor value it returned is now “0” which indicates that the scan is complete.
```

Podemos também usar o argumento COUNT para em vez de mostrar 10, mostrar mais ou menos de cada vez

**SET →** Set key to hold the string value. If key already holds a value, it is overwritten, regardless of its type.

**GET →** Vai buscar o valor associado à key que lhe colocar à frente

```jsx
127.0.0.1:6379> SET TestBook:1 "Elon Musk Book"
OK
127.0.0.1:6379> GET TestBoOk:1
(nil)
127.0.0.1:6379> GET TestBook:1
"Elon Musk Book"
127.0.0.1:6379> SET TestBook:1:Ler 1
OK
```

**INCRBY →** Incrementa o valor da chave inserida a seguir, pelo valor a seguir à chave (INCRBY Book:1:ReadAction 20), caso o valor da chave e o valor inserido sejam numéricos

**INCR →** Incrementa por uma unidade o valor da chave inserida, caso o valor da chave seja um número

DECR e DECRBY funciona no inverso

```jsx
127.0.0.1:6379> INCR TestBook:1:Ler
(integer) 2
127.0.0.1:6379> GET TestBook:1:Ler
"2"
127.0.0.1:6379> INCRBY TestBook:1:Ler 100
(integer) 102
127.0.0.1:6379> GET TestBook:1:Ler
"102"
127.0.0.1:6379> DECR TestBook:1:Ler
(integer) 101
127.0.0.1:6379> GET TestBook:1:Ler
"101"
127.0.0.1:6379> DECRBY TestBook:1:Ler 5
(integer) 96
127.0.0.1:6379> Get TestBook:1:Ler
"96"
127.0.0.1:6379> INCR TestBook:1 "okkk"
(error) ERR wrong number of arguments for 'incr' command
127.0.0.1:6379> INCR TestBook:1 2
(error) ERR wrong number of arguments for 'incr' command
127.0.0.1:6379> INCR TestBook:1
(error) ERR value is not an integer or out of range
```

**MSET →** Funciona igual ao SET mas permite colocar vários valores correspondentes a várias chaves

**MGET →** Funciona igual ao GET e permite ir buscar vários valores correspondentes a várias chaves

```jsx
127.0.0.1:6379> MSET Pessoa:1 "Musk Elon" Pessoa:2 "Hugo Paivex" Pessoa:3 "Luis Pinto" Pessoa:4 "Alex Bro"
OK

127.0.0.1:6379> MGET Pessoa:1 Pessoa:2 Pessoa:3
1) "Musk Elon"
2) "Hugo Paivex"
3) "Luis Pinto"
```

**LPUSH →** Insere no início da lista

**RPUSH →** Insere no fim da lista

**LRANGE →** Vai buscar os elementos numa lista desde a primeira posição especificada até à última posição especificada

```jsx
127.0.0.1:6379> LPUSH TestBook:1:comentario "Boa leitura"
(integer) 1
127.0.0.1:6379> LRANGE TestBook:1:comentario 0 -1
1) "Boa leitura"
127.0.0.1:6379> LPUSH TestBook:1:comentario "Muito longo!"
(integer) 2
127.0.0.1:6379>
127.0.0.1:6379> LRANGE TestBook:1:comentario 0 -1
1) "Muito longo!"
2) "Boa leitura"
127.0.0.1:6379> RPUSH TestBook:1:comentario "Commmmennnt!?"
(integer) 3
127.0.0.1:6379> LRANGE TestBook:1:comentario 0 -1
1) "Muito longo!"
2) "Boa leitura"
3) "Commmmennnt!?"
```

**LPOP →** Retira o primeiro elemento da lista

**RPOP →** Retira o último elemento da lista

```jsx
127.0.0.1:6379> LPOP TestBook:1:comentario
"Muito longo!"
127.0.0.1:6379> LRANGE TestBook:1:comentario 0 -1
1) "Boa leitura"
2) "Commmmennnt!?"
127.0.0.1:6379> RPOP TestBook:1:comentario
"Commmmennnt!?"
127.0.0.1:6379> LRANGE TestBook:1:comentario 0 -1
1) "Boa leitura"
```

**LTRIM →** replaces a list with a range from an existing list.

```jsx
127.0.0.1:6379> RPUSH Organization:1:membros Pessoa:1 Pessoa:2 Pessoa:3 Pessoa:4

(integer) 4
127.0.0.1:6379> LRANGE Organization:1:members  0 -1
(empty array)
127.0.0.1:6379> LRANGE Organization:1:members 0 -1
(empty array)
127.0.0.1:6379> LRANGE Organization:1:membros 0 -1
1) "Pessoa:1"
2) "Pessoa:2"
3) "Pessoa:3"
4) "Pessoa:4"
127.0.0.1:6379> LTRIM Organization:1:membros 0 2
OK
127.0.0.1:6379> LRANGE Organization:1:membros 0 -1
1) "Pessoa:1"
2) "Pessoa:2"
3) "Pessoa:3"
```

**BLPOP →** Similar ao LPOP mas funciona como um queue simples, vai bloquear de enviar um return ao cliente se a lista estiver vazia. Podemos fornecer um timeout e, caso o timeout passe the client **will unblock returning a nil multi-bulk value when the specified timeout has expired without a push operation against at least one of the specified keys**. Caso seja passado timeout 0, fica bloqueado indefinidamente até meterem um novo valor.

```jsx
127.0.0.1:6379> BRPOP Organization:1:membros 5
1) "Organization:1:membros"
2) "Pessoa:3"
127.0.0.1:6379> LRANGE Organization:1:members 0 -1
(empty array)
127.0.0.1:6379> LRANGE Organization:1:membros 0 -1
1) "Pessoa:1"
2) "Pessoa:2"
```

**HSET →** permite criar um hashmap com o valor respetivo de um fiel de uma key.

**HGET →** permite ir buscar o mencionado em cima

```jsx
127.0.0.1:6379> HSET TestBook:3 nome "Gatos"
(integer) 1
127.0.0.1:6379> HSET TestBook:3 nome
(error) ERR wrong number of arguments for 'hset' command
127.0.0.1:6379> HGET TestBook:3 nome
"Gatos"
```

**HMSET, HMGET e HGETALL →** Funcionam de maneira parecida ao SET e afins

```jsx
127.0.0.1:6379> HMSET TestBook:4 nome "Tintin" author "Luis Tintin" copyrightYear 1980 ISB 24124124
OK
127.0.0.1:6379> HMGET TestBook:4 author ISBN
1) "Luis Tintin"
2) (nil)
127.0.0.1:6379> HMGET TestBook:4 author ISB
1) "Luis Tintin"
2) "24124124"
127.0.0.1:6379> HGETALL TestBook:4
1) "nome"
2) "Tintin"
3) "author"
4) "Luis Tintin"
5) "copyrightYear"
6) "1980"
7) "ISB"
8) "24124124"
```

**HEXISTS →** Verifica se existe um filed

**HLEN →** Diz-me a quantidade de fields que uma key tem

```jsx
127.0.0.1:6379> HEXISTS TestBook:4 copyrightYear
(integer) 1
127.0.0.1:6379> HEXISTS TestBook:4 barcode
(integer) 0
127.0.0.1:6379> HLEN TestBook:4
(integer) 4
```

**HKEYS →** Retorna os fields de uma chave que representa uma hash

**HVALS →** Retorna os valores de todos os fields de uma chave que representa uma hash

```jsx
127.0.0.1:6379> HKEYS TestBook:4
1) "nome"
2) "author"
3) "copyrightYear"
4) "ISB"
127.0.0.1:6379> HVALS TestBook:4
1) "Tintin"
2) "Luis Tintin"
3) "1980"
4) "24124124"
```

**HDEL →** Elimina um field e por consequência o seu valor

**HINCRBY, HINCR... →** Funciona da mesma forma dita anteriormente mas para o field de uma hash passasda

```jsx
127.0.0.1:6379> HDEL TestBook:4 copyrightYear
(integer) 1
127.0.0.1:6379> HGETALL TestBook:4
1) "nome"
2) "Tintin"
3) "author"
4) "Luis Tintin"
5) "ISB"
6) "24124124"
127.0.0.1:6379> HSET TestBook:4 copyrightYear 1968
(integer) 1
127.0.0.1:6379> HGET TestBook:4 copyrightYear
"1968"
127.0.0.1:6379> HINCRBY TestBook:4 copyrightYear 1
(integer) 1969
127.0.0.1:6379> HGET TestBook:4 copyrightYear
"1969"
```

**SADD →** Adiciona um elemento ou mais a um SET

**SMEMBERS →** Display de todos os elementos de um SET

```jsx
127.0.0.1:6379> SET Organization:5 "Beatles"
OK
127.0.0.1:6379> SADD Organization:5:membros Paul John George Ringo
(integer) 4
127.0.0.1:6379> SMEMBERS Organization:5:membros
1) "John"
2) "Paul"
3) "Ringo"
4) "George"
```

**SISMEMBER →** Verifica se é elemento do SET

**SCARD →** Retorna o número de elementos de um SET

```jsx
127.0.0.1:6379> SISMEMBER Organization:5:membros "John"
(integer) 1
127.0.0.1:6379> SISMEMBER Organization:5:membros "Ralph"
(integer) 0
127.0.0.1:6379> SCARD Organization:5:membro
(integer) 0
127.0.0.1:6379> SCARD Organization:5:membros
(integer) 4
127.0.0.1:6379>
```

**SUNION →** Faz display da união dos Sets passados

**SINTER →** Faz display do elementos presentes em ambos os Sets passados

**SDIFF →** Faz display dos elementos não presentes em ambos os Sets passados

```jsx
127.0.0.1:6379> SUNION Organization:5:membros Organization:6:membros
1) "George"
2) "Paul"
3) "Denny"
4) "John"
5) "Linda"
6) "Ringo"
127.0.0.1:6379> SUNION Organization:6:membros Organization:7:membros
1) "Paul"
2) "Denny"
3) "Bob"
4) "George"
5) "Tom"
6) "Linda"
7) "Roy"
8) "Jeff"
127.0.0.1:6379> SUNION Organization:5:membros Organization:6:membros Organization:8
1) "George"
2) "Paul"
3) "Denny"
4) "John"
5) "Linda"
6) "Ringo"
127.0.0.1:6379> SINTER Organization:5:membros Organization:6:membros
1) "Paul"
127.0.0.1:6379> SINTER Organization:6:membros Organization:7:membros
(empty array)
127.0.0.1:6379> SDIFF Organization:5:membros: Organization:6:membros
(empty array)
127.0.0.1:6379> SDIFF Organization:5:membros Organization:6:membros
1) "John"
2) "Ringo"
3) "George"
127.0.0.1:6379>
```

**ZADD →** Adiciona a uma chave os respetivos scores e membros do SORTED SET

**ZRANGE →** Mostra os elementos do SORTED SET. Com WITHSCORES mostra os scores e não só os membros. Os membros aparecem ordenados pelo score de forma crescente.

**ZREVRANGE →**  Tudo igual ao de cima mas os membros aparecem ordenados pelo score de forma decrescente.

```jsx
127.0.0.1:6379> ZADD copyrightYear 1996 TestBook:1 2014 TestBook:2 1963 TestBook:3
(integer) 3
127.0.0.1:6379> ZADD copyrightYear 1969 TestBook:4
(integer) 1
127.0.0.1:6379> ZRANGE copyrightYear 0 -1
1) "TestBook:3"
2) "TestBook:4"
3) "TestBook:1"
4) "TestBook:2"
127.0.0.1:6379> ZRANGE copyrightYear 0 -1 [WITHSCORES]
(error) ERR syntax error
127.0.0.1:6379> ZRANGE copyrightYear 0 -1 WITHSCORES
1) "TestBook:3"
2) "1963"
3) "TestBook:4"
4) "1969"
5) "TestBook:1"
6) "1996"
7) "TestBook:2"
8) "2014"
127.0.0.1:6379> ZREVRANGE copyrightYear 0 -1 WITHSCORES
1) "TestBook:2"
2) "2014"
3) "TestBook:1"
4) "1996"
5) "TestBook:4"
6) "1969"
7) "TestBook:3"
8) "1963"
127.0.0.1:6379> ZREVRANGE copyrightYear 0 -1
1) "TestBook:2"
2) "TestBook:1"
3) "TestBook:4"
4) "TestBook:3"
```

No caso do score ser igual, o ZRANGE ordena pelos nomes dos membros.

```jsx
127.0.0.1:6379> ZADD TestBook:nomes 0 "Into the Wild" 0 "Gatos"
(integer) 2
127.0.0.1:6379> ZADD TestBook:nome 0 "Time machne" 0 "Gravity"
(integer) 2
127.0.0.1:6379> ZRANGE TestBook: nome 0 -1 WITHSCORES
(error) ERR value is not an integer or out of range
127.0.0.1:6379> ZRANGE TestBook:nome 0 -1 WITHSCORES
1) "Gravity"
2) "0"
3) "Time machne"
4) "0"
127.0.0.1:6379> ZRANGE TestBook:nomes 0 -1 WITHSCORES
1) "Gatos"
2) "0"
3) "Into the Wild"
4) "0"
```

**ZRANK →** Retorna o index da posição do rank do membro

**ZSCORE →** Retorna o score do membro

**ZREM →** Remove o membro do SORTED SET

```jsx
127.0.0.1:6379> ZRANK copyrightYear TestBook:3
(integer) 0
127.0.0.1:6379> ZRANK copyrightYear TestBook:1
(integer) 2
127.0.0.1:6379> ZRANK copyrightYear TestBook:56
(nil)
127.0.0.1:6379> ZRANK copyrightYear TestBook:3
(integer) 0
127.0.0.1:6379> ZRANK copyrightYear TestBook:1
(integer) 2
127.0.0.1:6379> ZSCORE copyrightYear TestBook:3
"1963"
127.0.0.1:6379> ZSCORE copyrightYear TestBook:1
"1996"
127.0.0.1:6379> ZRANGE copyrightYear 0 -1 WITHSCORES
1) "TestBook:3"
2) "1963"
3) "TestBook:4"
4) "1969"
5) "TestBook:1"
6) "1996"
7) "TestBook:2"
8) "2014"
```

**ZCARD →** Retorna o total de membros num SORTED SET

**ZCOUNT →** Retorna o némero de membros num range de score

**ZRANGEBYSCORE →** Ordena os membros entre scores

```jsx
127.0.0.1:6379> ZCOUNT copyrightYear 1900 1970
(integer) 2
127.0.0.1:6379> ZRANGEBYSCORE copyrightYear 1900 1970 WITHSCORES
1) "TestBook:3"
2) "1963"
3) "TestBook:4"
4) "1969"
127.0.0.1:6379> ZRANGEBYSCORE copyrightYear -inf 2000 WITHSCORES
1) "TestBook:3"
2) "1963"
3) "TestBook:4"
4) "1969"
5) "TestBook:1"
6) "1996"
127.0.0.1:6379> ZRANGEBYSCORE copyrightYear 1998 +inf WITHSCORES
1) "TestBook:2"
2) "2014"
127.0.0.1:6379> ZCARD copyrightYear
(integer) 4
```

**SETBIT →** Set a BIT com um offset

**GETBIT →** Ir buscar a uma posição do bit com o offset

```jsx
127.0.0.1:6379> SET Filme:1 "Blade Runner"
OK
127.0.0.1:6379> HSET Movie:2 name "Star Wars"
(integer) 1
127.0.0.1:6379> SADD Movie:3 "2001 a Space Odyssey"
(integer) 1
127.0.0.1:6379> SETBIT Movie:UserPlays:2014-12-11 2 1
(integer) 0
127.0.0.1:6379> GETBIT Movie:UserPLAYS:2014-12-11 2
(integer) 0
127.0.0.1:6379> GETBIT Movie:UserPlays:2014-12-11 2
(integer) 1
```

**BITCOUNT →** Retorna o número de bits que estão colocado em 1

**BITPOST →** Retorna o primeiro offset que seja 0 ou 1

```jsx
127.0.0.1:6379> BITCOUNT Movie:UserPlays:2014-12-11
(integer) 1
127.0.0.1:6379> BITPOS MOVIE:UserPlays:2014-12-11 1
(integer) -1
127.0.0.1:6379> BITPOS Movie:UserPlays:2014-12-11 1
(integer) 2
127.0.0.1:6379> BITPOS Movie:UserPlays:2014-12-11 0
(integer) 0
```

**BITTOP →** Permite usar operações de bit: AND, OR, XOR e NOT

```jsx
127.0.0.1:6379>  SETBIT Movie:UserPlays:2014-12-12 2 1
(integer) 0
127.0.0.1:6379> SETBIT Movie:UserPlays:2014-12-12 1 1
(integer) 0
127.0.0.1:6379> BITOP AND and_result Movie:UserPlays:2014-12-11 Movie:UserPlays:2014-12-12
(integer) 1
127.0.0.1:6379> GETBIT and_result 1
(integer) 0
127.0.0.1:6379> GETBIT and_result 2
(integer) 1
127.0.0.1:6379> GETBIT and_result 3
(integer) 0
```

**PFADD** adds one or more elements to a HyperLogLogs. 

The **PFCOUNT** returns an approximation of the count in the HyperLogLogs with an standard error of .81%. 

With **PFMERGE** multiple HyperLogLogs can be merged into a single HyperLogLogs. 

The advantage of the HyperLogLogs is that is very efficient in memory (maximum size is 12k bytes) and does not require an proportional amount of memory (memory to items in the set) to perform a population count.

```jsx
127.0.0.1:6379> PFADD EducationEvent:1:attendee Person:1 Person:2 Person:3 Person:44556
(integer) 1
127.0.0.1:6379> PFCOUNT EducationEvent:1:attendee
(integer) 4
127.0.0.1:6379> PFADD LiteraryEvent:1:attendee Person:4 Person:1
(integer) 1
127.0.0.1:6379> PFMERGE Event:attendee EducationEvent:1:attendee LiteraryEvent:1:attendee
OK
127.0.0.1:6379> PFCOUNT Event:attendee
(integer) 5
```