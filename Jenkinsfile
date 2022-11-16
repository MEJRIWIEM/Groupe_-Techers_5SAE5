pipeline {
    agent any
    
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M2_HOME"
    }
environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "172.10.0.140:8081"
        NEXUS_REPOSITORY = "java-app"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
        registry = "alaamoalla/springboot-achat" 
        registryCredential = 'dockerHub' 
        dockerImage = '' 
    }

    stages {
        stage('GIT'){
            steps{
                echo 'Getting Project from GIT';
                git branch:"AlaaMoalla",
                url:'https://github.com/MEJRIWIEM/Groupe_Techers_5SAE5.git'
            }
}
stage('MVN CLEAN'){
    steps{
        sh 'mvn clean'
    }
}
stage('MVN COMPILE'){
    steps{
        sh 'mvn compile'
    }
}
stage('MVN TEST'){
    steps{
        sh 'mvn test'
    }
}
stage("Maven Build") {
            steps {
                script {
                    sh "mvn package -DskipTests=true"
                }
            }
        }
        
        stage('MVN SONARQUBE'){
    steps{
        sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
    }
}
        
        stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
        stage('Building our image') { 

            steps { 

                script { 

                    dockerImage = docker.build("alaamoalla/springboot-achat:latest")  

                }

            } 

        }

        stage('Deploy our image') { 

            steps { 

                script { 

                    docker.withRegistry( '', registryCredential ) { 

                        dockerImage.push() 

                    }

                } 

            }

        } 

        stage('Cleaning up') { 

            steps { 

                sh "docker rmi alaamoalla/springboot-achat:achat-spring" 

            }

        } 

}

 post {
        always {
            echo 'One way or another, I have finished'
            deleteDir() /* clean up our workspace */
        }
        success {
            echo 'I succeeded!'
             mail to: 'alaa.moalla.esprit@yopmail.com',
             subject: "The Pipeline: ${currentBuild.fullDisplayName} succeeded",
             body: "The Pipeline ${env.BUILD_URL} completed successfully."
        }
        unstable {
            echo 'I am unstable :/'
             mail to: 'alaa.moalla.esprit@yopmail.com',
             subject: "Unstable Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
        failure {
            echo 'I failed :('
             mail to: 'alaa.moalla.esprit@yopmail.com',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
        changed {
            echo 'Things were different before...'
        }
    }
    }