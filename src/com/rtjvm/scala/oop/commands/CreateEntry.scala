package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {


  // Main apply method
  override def apply(state: State): State = {
    val wd = state.wd
    if(wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exsits!")
    }
    else if ( name.contains(Directory.SEPARATOR) ) {
      state.setMessage(name + " must not contain separators.")
    }
    else if(checkIllegal(name)) {
      state.setMessage(name + ": illegal name entry.")
    }
    else {
      doCreateEntry(state, this.name)
    }
  }

  // Helper method
  def checkIllegal(str: String): Boolean = {
    name.contains(".")
  }


  // Helper method
  def doCreateEntry(state: State, str: String): State = {
    // making type parameters as general as possible is usually a good practice
    //  currentDirectory = state.root ; wdpath = wd List ; newEntry = new Directory
    def updateStructure(currentDirectory: Directory, wdpath: List[String], newEntry: DirEntry): Directory = {

      if (wdpath.isEmpty)
        currentDirectory.addEntry(newEntry)
      else{
        val oldEntry = currentDirectory.findEntry(wdpath.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, wdpath.tail, newEntry))
        /*
        need to find the head of the path:
          currentDirectory = /a
          path = ["b"]
        */
      }
      /*
      someDir:
        /a/b
          /c
          /d
          (new) /e
        =>
        new /b (parent a)
            /c
            /d
            /e

       */
    }

    val wd = state.wd
    val fullPath = wd.path

    // 1) Retrieve all the directories in the current FULL PATH (list of all the tokens)
    val allDirsInPath = wd.getAllFoldersInPath

    // 2) Create new directory in the working directory

    // TODO implement this
    val newEntry: DirEntry = createSpecificEntry(state)

    // 3) Update the directory structure
    //   Remember that the directory structure is immutable
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4) Find new working directory (wd) instance within the newly created directory structure ("navigate to the directory within the new structure")
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)

    // state.setMessage("Directory [" + name + "] has been created")
  }

  def createSpecificEntry(state: State): DirEntry
}
