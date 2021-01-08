fun main() {
   //Входные данные в чате
    val listOfUsers: List<String> = mutableListOf("Alex", "Ann", "Masha", "Gleb")

    val listOfMesses: ArrayList<Message> = ArrayList()
    with(listOfMesses) {
        add(Message("Alex", "Ann", " ya priehala", false))
        add(Message("Alex", "Ann", " gde kot?", true))
        add(Message("Ann", "Alex", " kott?", true))
        add(Message("Alex", "Masha", "Я купила торт", false))
        add(Message("Alex", "Masha", "poshli est tort", false))
        add(Message("Alex", "Gleb", "Как дела", false))
        add(Message("Alex", "Gleb", "Пошли в плейстешен играть", false))
        add(Message("Masha", "Gleb", "нет конфет", false))
    }
    //Старт чата
    welcomeScreen(MessageReader(listOfMesses, listOfUsers))
    println("Выход...")
}

fun welcomeScreen(msgR: MessageReader) {
    do {
        println(
            """Добро пожаловть в чат! Вот список зарегистрировнных пользователей.
Напишите: login Alex, например. (пароль не нужен) или exit для выхода"""
        )

        msgR.getUsersList().forEach { println(it) }

        val input = readLine()!!.split(' ')
        if (input[0]=="exit") return
        parseChatScreen(input, msgR)

    } while (true)

}

private fun parseChatScreen(input: List<String>, msgR: MessageReader) {
    if (input[0] == "login".toLowerCase() &&
        msgR.getUsersList().contains(input[1])
    ) {
        println("Вы залогинились как ${input[1]}. Ваши сообщения:")
        chatsScreen(msgR, input[1])
    } else {
        println("Нет такого пользователя. попробуйте еще\n")
    }
}

fun chatsScreen(msgR: MessageReader, toUser: String) {
    do {
        val messages = msgR.filterMsgsForUser(toUser  )
        val returnListOfMessages: ArrayList<Message> = ArrayList()

    for (element in messages.size - 1 downTo 0) {
        if (!contain(toUser, messages[element].from, returnListOfMessages))
            returnListOfMessages.add(messages[element])
    }

    returnListOfMessages.forEach {
        println("=================================================\n $it") }
        println("\nВведите read nick, например read Masha или команду back чтобы вернуться ")

         val input = readLine()!!.split(' ')
        if (input[0]=="back") return
        parseMessagesScreen(input, msgR, toUser)
    } while (true)

    }

private fun parseMessagesScreen(
    input: List<String>,
    msgR: MessageReader,
    toUser: String
) {
    if (input[0] == "read" && msgR.getUsersList().contains(input[1])) {
        messagesScreen(msgR, toUser, input[1])
    }
}

fun messagesScreen(msgR: MessageReader, user: String, fromUser: String){

    do{
        msgR.displayMsgs(user, fromUser).forEach {
            //if (it.from == user) it.new = false;
            println(it);
            it.new = false; }
        println()
        println("Введите msg сообщение чтобы написать пользователю \n" +
                "введите back чтобы вернуться на предыдущий экран. " +
                "Чтобы написать новому пользователю: new Nick сообщение " +
                "(затем перелогинтесь этим пользователем чтобы прочитать")

     try {
         val input = readLine()!!.split(' ')

         if (input[0] == "back") return;

         if (input[0] == "msg") {
             msgR.addMessage(Message(fromUser, user, concatStr(input), true));
         }
         if (input[0] == "new" && msgR.getUsersList().contains(input[1])) {

             msgR.addMessage(Message(user, input[1], concatStr(input), true));
             println("Отправлено")
         }
     } catch (e: IndexOutOfBoundsException){
         continue
     }

    } while (true)


}
fun concatStr(array: List<String>):String{
    var res : String =""
    for (i in 2..array.size-1){
       res = res.plus("${array[i]} ")
    }
    return res
}

fun contain(user :String, name: String, list: ArrayList<Message>): Boolean {
    for (i in 0..list.size - 1) {
        if ((list[i].from == name) || (list[i].from == user && list[i].to == name )) //
            return true
    }
    return false
}


class MessageReader(
   private val listOfMsgs: ArrayList<Message>,
   private val listOfUsers: List<String>
) {


    fun displayMsgs(to: String, from: String): List<Message> {
        return listOfMsgs.filter { (it.to == to && it.from == from)
                || (it.to == from && it.from == to)
        }

    }

    /**
     * Показать все сообщения адресованные мне (залогиненому юзеру)
     */
    fun filterMsgsForUser(to: String ): List<Message> {
        return listOfMsgs.filter { ((it.to == to) || (it.from == to))   }//

    }

    fun getUsersList(): List<String> = listOfUsers
    fun addMessage(message: Message) {
        listOfMsgs.add(message)
    }


}