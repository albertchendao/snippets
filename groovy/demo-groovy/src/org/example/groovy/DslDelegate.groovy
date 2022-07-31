package org.example.groovy

abstract class DslDelegate extends Script {
    def setName(String name){
        println name
    }
}
