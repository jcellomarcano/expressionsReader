import java.util.*
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

// Instance variables
val engine: ScriptEngine = ScriptEngineManager().getEngineByName("JavaScript")
const val evaluator = "EVAL"
const val show = "MOSTRAR"
const val leave = "SALIR"

fun main() {


    while (true){
        val input: ArrayList<String> = ArrayList(readLine()!!.split(' '))

        val command: String = input[0]
        val typeFormat: String = input[1]
        val expression: ArrayList<String> = ArrayList(input.subList(2, input.size))
        if (checkExpresion(expression, typeFormat) && checkTypeFormat(typeFormat) && checkCommand(command)){
            when (command) {
                evaluator -> {
                    println(eval(reformatExpresion(typeFormat,  expression)))
                }
                show -> {
                    println(reformatExpresion(typeFormat,  expression))
                }
                leave -> {
                    return
                }
            }

        } else {
            println("Sorry, your expression has more operators than fits in this expression")
        }
    }


}

fun reformatExpresion(orderType: String, expression: ArrayList<String>): String {

    return when (orderType) {
        "PRE" -> {
            val preOrderFormat = PreOrderFormat()

            preOrderFormat.extractFromPreOrder (expression)
        }
        "POST" -> {
            val postOrderFormat = PostOrderFormat()
            postOrderFormat.extractFromPostOrder(expression)
        }
        else -> {
            println("We're sorry, internal error, try again using PRE or POST statement")
            ""
        }
    }
}
fun checkExpresion(expression: ArrayList<String>, typeFormat: String): Boolean {
    val operatorList = arrayListOf("*","/","+","-")
    val numberList = arrayListOf("1","2","3","4","5","6","7","8","9","0")
    var countSymbol = 0
    var countNumber = 0
    when {
        expression[0] !in operatorList && typeFormat == "PRE" -> {
            return false
        }
        else -> {
            for (symbol in expression){
                when (symbol) {
                    in operatorList -> {
                        countSymbol += 1
                    }
                    in numberList -> {
                        countNumber += 1
                    }
                    else -> {
                        return false
                    }
                }
            }
        }
    }

//    println(countNumber)
//    println(countSymbol)
    return countSymbol == (countNumber - 1)
}

fun checkTypeFormat(typeFormat:String):Boolean{
    val typeList = arrayListOf("PRE","POST")
    return typeFormat in typeList
}
fun checkCommand(command:String):Boolean{
    val commandList = arrayListOf(evaluator, show, leave)
    return command in commandList
}
/**
 * Evaluates a mathematical expression.
 */
fun eval(expr: String): Number {
    return try {
        engine.eval(expr) as Number
    } catch (ex: ScriptException) {
        Double.NaN
    }
}
