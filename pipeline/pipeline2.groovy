if (params.Continuar != 'Sim') {
	currentBuild.result = 'UNSTABLE'
} else {
	node {
        def nomeEstagio = ''
		def seqEstagio = 1

        ansiColor('xterm') {
            try {
                nomeEstagio = 'Estagio ' + seqEstagio++
                stage(nomeEstagio) {
                    echo '\033[1;33mIniciando \033[0m' + nomeEstagio
                    writeFile file:nomeEstagio + '.txt', text:nomeEstagio, encoding:'UTF-8'
                    echo nomeEstagio + '\033[1;32m Concluido\033[0m'
                }
                nomeEstagio = 'Estagio ' + seqEstagio++
                stage(nomeEstagio) {
                    echo '\033[1;33mIniciando \033[0m' + nomeEstagio
                    writeFile file:nomeEstagio + '.txt', text:nomeEstagio, encoding:'UTF-8'
                    parallel (
                        (nomeEstagio + '.1'): {
                            writeFile file:nomeEstagio + '.1.txt', text:nomeEstagio + '.1', encoding:'UTF-8'
                        }, (nomeEstagio + '.2'): {
                            writeFile file:nomeEstagio + '.2.txt', text:nomeEstagio + '.2', encoding:'UTF-8'
                        }, (nomeEstagio + '.3'): {
                            writeFile file:nomeEstagio + '.3.txt', text:nomeEstagio + '.3', encoding:'UTF-8'
                        }
                    )
                    echo nomeEstagio + '\033[1;32m Concluido\033[0m'
                }
                nomeEstagio = 'Estagio ' + seqEstagio++
                stage(nomeEstagio) {
                    echo '\033[1;33mIniciando \033[0m' + nomeEstagio
                    writeFile file:nomeEstagio + '.txt', text:nomeEstagio, encoding:'UTF-8'
                    echo nomeEstagio + '\033[1;32m Concluido\033[0m'
                }
            } catch (error) {
                echo '\n\n\033[1;31m[Erro]\033[0m Erro no ' + nomeEstagio + '\n\n'
                echo error.toString()
                currentBuild.result = 'FAILURE'
            }
			archiveArtifacts '*.txt'
			office365ConnectorSend message: 'Execucao Concluida.', status: 'End', webhookUrl: 'https://outlook.office.com/webhook/0b7a3292-79a3-4920-bc71-47fcd4555102@93f33571-550f-43cf-b09f-cd331338d086/JenkinsCI/2b011cf39b63478f86dd0b0791675688/0b7a3292-79a3-4920-bc71-47fcd4555102'
        }
	}
}
