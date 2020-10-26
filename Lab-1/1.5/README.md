## Estruturas de Dados
**Foram usadas três estruturas de dados:**

- ***SET - MNUsers e MNFollowing:|username|***
	- Set com o username de todos os utilizadores e Set com os utilizadores que um dado user *|username|* segue, respetivamente

- ***LIST - MNMessages:|username|*** 
	- Lista com as mensagens enviadas ao sistema pelo utilizador *|username|*

  

- ***HASH - MNUser:|username|***

	- Hash com informações sobre o *|username|* tais como: Nome, Idade e Hobby

  

**Com estas estruturas de dados, o sistema tem as seguintes funcionalidades**

- Ver o perfil pessoal do utilizador e de outras pessoas

- Seguir um utilizador

- Deixar de seguir um utilizador

- Enviar uma mensagem para o sistema

- Ver as mensagens recentes dos utilizadores que o utilizador segue

- Ver as mensagens recentes que o utilizador enviou para o sistema

- Listar todos os utilizadores

  

## Exemplos

  

    Insert your username: hugofpaiva
    
    Insert your name: Hugo Paiva
    
    Insert your age: 20
    
    Insert you hobby: Swimming
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 1
    
      
    
    -- hugofpaiva Info --
    
    Name: Hugo Paiva
    
    Age: 20
    
    Hobby: Swimming
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 2
    
      
    
    There are no users!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 3
    
      
    
    You are following no users!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 4
    
      
    
    -- Sending a message --
    
    Insert message: Olá!
    
    Message sent!
    
      
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 5
    
      
    
    You are following no users!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 6
    
      
    
    -- My Last 5 messages --
    
    - Olá!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 7
    
      
    
    -- Users on the system --
    
    1 - hugofpaiva
    
    Insert number to see info or 0 to exit: 0
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: q
    
    Come back soon, hugofpaiva!
    
      
-----------------


  

    Insert your username: paiva
    
    Insert your name: João Paiva
    
    Insert your age: 21
    
    Insert you hobby: Read
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 1
    
      
    
    -- paiva Info --
    
    Name: João Paiva
    
    Age: 21
    
    Hobby: Read
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 2
    
      
    
    -- Users to follow --
    
    1 - hugofpaiva
    
    Insert number to follow: 1
    
    hugofpaiva followed!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 3
    
      
    
    -- Users to unfollow --
    
    1 - hugofpaiva
    
    Insert number to follow: 1
    
    hugofpaiva unfollowed!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 2
    
      
    
    -- Users to follow --
    
    1 - hugofpaiva
    
    Insert number to follow: 1
    
    hugofpaiva followed!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 4
    
      
    
    -- Sending a message --
    
    Insert message: Hello!
    
    Message sent!
    
      
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 5
    
      
    
    -- Last 5 messages from hugofpaiva --
    
    - Olá!
    
      
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 6
    
      
    
    -- My Last 5 messages --
    
    - Hello!
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: 7
    
      
    
    -- Users on the system --
    
    1 - hugofpaiva
    
    2 - paiva
    
    Insert number to see info or 0 to exit: 1
    
      
    
    -- hugofpaiva Info --
    
    Name: Hugo Paiva
    
    Age: 20
    
    Hobby: Swimming
    
      
    
    -- Select an option --
    
    1 - My Profile
    
    2 - Follow user
    
    3 - Unfollow user
    
    4 - Send Message
    
    5 - Read Subscriptions
    
    6 - Read my messages
    
    7 - List all users
    
    q - Quit
    
    Option: q
    
    Come back soon, paiva!
