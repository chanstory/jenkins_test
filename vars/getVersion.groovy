import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import java.io.File

def call(String repositoryName, String version) {
    lock(resource: "lock-version") {
        def filePath = "data_files/versions"
        def file = new File(filePath)

        try {
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs()
            }

            def versionObj = {}
            if (file.exists()) {
                def content = file.text.trim()
                def slurper = new JsonSlurper()
                versionObj =  slurper.parseText(content)
            }

            versionObj[repositoryName] = version
            def jsonStr = JsonOutput.toJson(versionObj)
            writeFile(file: filePath, text: jsonStr)
        } catch (Exception e) {
            echo "error cause write version file. ${repositoryName}, ${version}"
            throw e
        }
    }
}