class Network {
	def us = InetAddress.localHost.hostAddress.toString()
	def ourPort
	def hostList
	
	def Network(port, hostAndPorts) {
		this.ourPort = port
		hostList = hostAndPorts.collect { [host:convertTo(it.split(':')[0]), port:Integer.valueOf(it.split(':')[1])]}

		Thread.start {
			println "Handshaking..."
			for(host in hostList) {
				handShake(host.host, host.port)
			}
		}

		Thread.start {
			while(true) {
				println "Accepting on port $ourPort"	
				def serverSocket = new ServerSocket(ourPort)
				serverSocket.accept { socket ->
					println "Connection from ${socket.inetAddress}"
					def hosts = socket.inputStream.text
					println "Got $hosts"
					hosts.eachMatch(/\[host:(.*?), port:(.*?)\]/) { match ->
						addHost(match[1].toString(), Integer.valueOf(match[2]))
					}
				}
				serverSocket.close()
			}
		}
	}

	def convertTo(host) {
		return (host == '127.0.0.1' || host == 'localhost' ? us : host)
	}

	synchronized def handShake(host, port) {
		println "Connecting to server $host:$port"
		Thread.start {
			new Socket(host, port).withStreams { input, output ->
				output << (hostList + [host:us, port:ourPort])
				def entry = [host:host, port:port]	
				if (!hostList.contains(entry)) {
					hostList << entry
					println "Hosts: $hostList"
				}
			}
		}
	}

	synchronized def addHost(host, port) {
		def entry = [host:convertTo(host), port:port]
		if (!hostList.contains(entry) && entry != [host:us, port:ourPort]) {
			handShake(entry.host, entry.port)
		}
	}
}

new Network(Integer.valueOf(this.args[0]), (args.size() > 1 ? args[1..-1] : []))