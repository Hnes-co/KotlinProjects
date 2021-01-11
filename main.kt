import java.io.File
import java.io.FileReader
import java.io.FileWriter

// Tehtäväolion luokkamäärittely
class ToDoObject (
    val title: String,
    var completed: Boolean
)
    


//Funktio uuden tehtäväolion luomiseen
fun createToDoItem(title: String, completed: Boolean): ToDoObject = ToDoObject(title, completed)

fun main() {  //-------------pääohjelma-----------------------//

    // luodaan muokattaava lista, johon tehtäväoliot tallennetaan
    var toDoList: MutableList<ToDoObject> = mutableListOf()


    println("Choose an action to perform on the list.")
    println("Add new task(a), mark a task completed(m), delete a task(d), print the list(p), empty the list (e), Quit(q)")
    var action = readLine()

    while(action.toString() != "q") // While-looppi listan käsittelyyn
    {
        when(action) {
            "a" -> { // UUDEN TEHTÄVÄN LISÄYS
                println("\n" + "Give the title of the new task:")
                val title = readLine()
                toDoList.add(createToDoItem(title.toString(), false))

                var fileWrite = FileWriter("toDoList.txt", true)
                fileWrite.write("${title.toString()}: not completed. \n")
                fileWrite.close()

                println("Task added!")
            }
            "m" -> { // TEHTÄVÄN MERKINTÄ TEHDYKSI
                println("\n" + "Give the index of the task to mark:")
                val index = Integer.valueOf(readLine())
                if(index <= toDoList.lastIndex) { // Tarkistetaan onko listassa annettu indeksi
                    toDoList[index].completed = true
                    // KIRJOITETAAN TIEDOSTO UUDESTAAN PÄIVITETYILLÄ TIEDOILLA
                    try{
                        var fileWrite = FileWriter("toDoList.txt")
                        toDoList.forEach {n ->
                            if(n.completed) fileWrite.write("${n.title}: completed. \n")
                            else fileWrite.write("${n.title}: not completed. \n")
                        }
                        fileWrite.close()
                    }
                    catch (ex:Exception){
                        print(ex.message)
                    }

                    println("Marked!")
                }
                else println("Invalid index number.") // Jos ei, kerrotaan käyttäjälle

            }
            "d" -> { // TEHTÄVÄN POISTO
                println("\n" + "Give the index of the task to delete:")
                val index = Integer.valueOf(readLine())
                if(index <= toDoList.lastIndex) { // Tarkistetaan onko listassa annettu indeksi
                    toDoList.removeAt(index.toInt())

                    // KIRJOITETAAN TIEDOSTO UUDESTAAN PÄIVITETYILLÄ TIEDOILLA
                    try{
                        var fileWrite = FileWriter("toDoList.txt")
                        toDoList.forEach {n ->
                            if(n.completed) fileWrite.write("${n.title}: completed. \n")
                            else fileWrite.write("${n.title}: not completed. \n")
                        }
                        fileWrite.close()
                    }
                    catch (ex:Exception){
                        print(ex.message)
                    }
                    println("Task deleted!")
                }
                else println("Invalid index number.") // Jos ei, kerrotaan käyttäjälle
            }
            "p" -> { // Tiedoston luku ja tulostus
                println("\n" + "Data read from toDoList.txt: \n")

                try{
                    var fileRead = FileReader("toDoList.txt")
                    var file:Int
                    do {
                        file=fileRead.read()
                        print(file.toChar())

                    } while (file!=-1)
                }
                catch (ex:Exception){
                    print(ex.message)
                }
            }
            "e" -> { // Tiedoston nollaus
                println("\n" + "List file cleared")

                try{
                    var fileWrite = FileWriter("toDoList.txt") // Luo uuden samannimisen tiedoston ja korvaa vanhan.
                    fileWrite.write("")
                    fileWrite.close()
                }
                catch (ex:Exception){
                    print(ex.message)
                }

            }
            else -> println("Invalid input.")
        }
        println("\n" + "Choose action:")
        action = readLine()
    }

}