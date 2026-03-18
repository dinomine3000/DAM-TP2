package dam.a51728.dataPipeline

import java.util.function.Function


class Pipeline(private val steps: ArrayList<(List<String>) -> List<String>>, private val stageNames: ArrayList<String>) {
    constructor() : this(ArrayList(), ArrayList())

    fun addStage(name: String, transform: (List<String>) -> List<String>): Pipeline{
        steps.add(transform)
        stageNames.add(name)
        return this
    }

    fun execute(input: List<String>): List<String>{
        return steps.runningFold(input) {
                                        acc, function ->
            function.invoke(acc)
        }.last()
    }

    fun describe(){
        println("Pipeline stages:")
        var i = 0
        for(step in stageNames){
            println("\t${++i}. $step")
        }
    }

    fun compose(stage1: String, stage2: String){
        val pair: ArrayList<(List<String>) -> List<String>> = ArrayList()
        for((i, step) in steps.withIndex()){
            val name = stageNames[i]
            if(name == stage1 || name == stage2) {
                pair.add(step)

            }
            if(pair.size == 2) break
        }
        if(pair.size < 2){
            error("Could not find 2 stages with names $stage1 and $stage2")
        }

    }
}