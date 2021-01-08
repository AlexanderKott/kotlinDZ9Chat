import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ChatTests{


    @Test
    fun test(){

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
         val list =  MessageReader(listOfMesses, listOfUsers)
             .filterMsgsForUser("Ann") //Проверяем что сообщения для Ann отфильтровались

        Assertions.assertEquals(list.contains(
            Message("Alex", "Ann", " gde kot?", true)),true)
    }

}