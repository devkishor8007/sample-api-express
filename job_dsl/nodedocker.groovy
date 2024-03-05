job('nodejs project one') {
    description('clone from github, build and push it to docker hub')
    scm {
        git('https://github.com/devkishor8007/sample-api-express.git', 'master') { node ->
            node / gitConfigName('devkishor8007')
            node / gitConfigEmail('')
        }
    }
    wrappers {
        nodejs('node 18')
        credentialsBinding {
            usernamePassword('USERNAME_DOC', 'PASS_DOC', 'dockerhubcred')
        }
    }
    steps {
        shell('npm install')
        shell('docker login -u ${USERNAME_DOC} -p ${PASS_DOC}')
        dockerBuildAndPublish {
            repositoryName('devkishor8007/sample-api-express')
            tag('${BUILD_NUMBER}')
            registryCredentials('dockerhubcred')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()

        }
        shell('docker logout')
    }
}