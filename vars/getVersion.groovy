import groovy.json.JsonSlurper
import java.io.File

// 동일한 resourceName 의 JOB이 동시에 실행되지 않음을 가정하고 read 하는 함수에는 lock을 걸지 않음
def call(String repositoryName) {
    echo "update test"
    def filePath = "data_files/versions"
    def file = new File(filePath)

    def versionObj = {}
    if (file.exists()) {
        try {
            def content = file.text.trim()
            def slurper = new JsonSlurper()
            versionObj =  slurper.parseText(content)
        } catch (Exception e) {
            echo "${filePath}: file format is not json"
            throw e
        } 
    }

    if (versionObj.containsKey(repositoryName)) {
        return versionObj[repositoryName]
    }

    return ""
}