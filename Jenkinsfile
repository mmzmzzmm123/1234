pipeline {
    agent any

    stages {
        stage('拉取代码') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'c96a3fdf-c7c0-4d26-816f-5f77f54c11aa', url: 'git@gitee.com:y_project/RuoYi-Vue.git']]])
                echo '拉去代码成功'
            }
        }
        stage('编译代码') {
            steps {
				sh "mvn clean package -Dmaven.test.skip=true"
                echo '编译代码成功'
            }
        }
        stage('发布代码') {
            steps {
				echo '代码发布成功'
            }
        }		
    }
}
