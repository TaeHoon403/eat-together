package coma.spring.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.springframework.beans.factory.annotation.Autowired;

public class HttpSessionConfigurator extends Configurator{
	@Autowired
	private HttpSession session;
	@Override
	public void modifyHandshake(
			ServerEndpointConfig sec,
			HandshakeRequest request,
			HandshakeResponse response) {
		HttpSession session =(HttpSession)request.getHttpSession();
		sec.getUserProperties().put("session",session);
	}
}
