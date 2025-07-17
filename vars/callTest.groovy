def call() {
    echo "call function excuted"
    def response = sh(script: "pwd", returnStdout: true).trim()
    println(response as String)
}