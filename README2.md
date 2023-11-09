1.º Entidades
-Task
-User

2.º Relacionamentos
-1X1
-1XN
-NXN

3.º Classes
-User Manager	List<User>
-Task Manager	List <Task>
-To Do Manager	List<UserTask>

4.º Metodos 
TaskManager:
	Create (nome: String, descriçao : String) : Task
	Delete(ID: int) : Void
	Update(Task:Task):Task
	Update(descriçao:String, Task_ID: int) : Task


UserManager:
CreateAccount(nome: String, password: String)
CreatePassword(nome: String, password: String)
ResetAccount(ID: user) 
DeleteAccount(ID: user)
RetrieveAccount(ID: user)


CRUD

Main
 1.º Instanciar dependencias
---    (while)\
| 2.º Declarar Menus
| 3.º Input User
| 4.º Chamar metodo de uma dependencia
| 5.º Tratar retornos (Success/Fail)
---