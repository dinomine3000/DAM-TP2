package dam.a51728.dataPipeline

fun buildPipeline(function: (pipeline: Pipeline) -> Pipeline): Pipeline{
    return function.invoke(Pipeline())
}

fun main(){
    val pipeline: Pipeline = buildPipeline{pipeline: Pipeline ->
        pipeline
            .addStage("Trim"){
                list ->
                val res = ArrayList<String>()
                for(line in list){
                    res.add(line.trim())
                }
                return@addStage res
            }
            .addStage("Filter errors"){
                    list ->
                val res = ArrayList<String>()
                for(line in list){
                    if(line.contains("ERROR")) res.add(line)
                }
                return@addStage res
            }
            .addStage("Uppercase"){
                    list ->
                val res = ArrayList<String>()
                for(line in list){
                    res.add(line.uppercase())
                }
                return@addStage res
            }
            .addStage("Add index"){
                    list ->
                val res = ArrayList<String>()
                for((i, line) in list.withIndex()){
                    res.add("${i + 1}. $line")
                }
                return@addStage res
            }
    }
    val logs = listOf (
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )
    pipeline.describe()
    val res = pipeline.execute(logs)
    println("Result:")
    for(line in res){
        println("\t$line")
    }
}