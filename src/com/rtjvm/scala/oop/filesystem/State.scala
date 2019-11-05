package com.rtjvm.scala.oop.filesystem
import com.rtjvm.scala.oop.files.Directory

// will hold the root directory and current directory
class State(
             val root: Directory,
             val wd: Directory, // Working Directory
             val output: String) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State = {
    State(root, wd, message) // This calls the apply() method below, in object State
  }
}

// remember that OBJECTS ARE STATIC
object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State = {
    new State(root, wd, output)

  }
}

