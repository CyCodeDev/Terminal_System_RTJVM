package com.rtjvm.scala.oop.filesystem
import java.util.Scanner

import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.commands.Command


object FileSystem extends App {
  val root = Directory.ROOT
  /*
  var state = State(root, root)
  //scanner object
  val scanner = new Scanner(System.in)

  while(true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
  */

  io.Source.stdin.getLines().foldLeft(State(root, root))( (currentState, newLine) => {
    currentState.show
    Command.from(newLine).apply(currentState)
  })
}