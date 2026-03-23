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
        var idx1 = -1
        var idx2 = -1
        for((i, _) in steps.withIndex()){
            val name = stageNames[i]
            if(name == stage1 && idx1 == -1) {
                idx1 = i
            }
            if(name == stage2 && idx2 == -1) {
                idx2 = i
            }
            if(idx1 != -1 && idx2 != -1) break
        }
        if(idx1 == -1 || idx2 == -1){
            error("Could not find 2 stages with names $stage1 and $stage2")
        }
        val stage1Fun: (List<String>) -> List<String>  = steps[idx1]
        val stage2Fun: (List<String>) -> List<String>  = steps[idx2]
        val name1: String = stageNames[idx1]
        val name2: String = stageNames[idx2]

        steps.removeAt(idx1)
        stageNames.removeAt(idx1)
        steps.removeAt(idx2 - 1)
        stageNames.removeAt(idx2 - 1)

        val newStage: (List<String>) -> List<String> = {lis -> stage2Fun.invoke(stage1Fun.invoke(lis))}
        val newName: String = "$name1 and then $name2"
        steps.add(newStage)
        stageNames.add(newName)
    }
}