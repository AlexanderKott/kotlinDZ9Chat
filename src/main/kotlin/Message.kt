data class Message (
    var to : String,
    var from : String,
    var text : String,
    var new  : Boolean,
        )
{

    override fun toString(): String {
          return "to $to from:$from   \"$text\" ${if (new) "NEW" else "" } "

    }

}