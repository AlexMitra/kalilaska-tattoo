package by.kalilaska.ktattoo.controller;



import by.kalilaska.ktattoo.manager.PathBodyManager;
import by.kalilaska.ktattoo.manager.PathViewManager;
import by.kalilaska.ktattoo.pathlist.PathBodyList;
import by.kalilaska.ktattoo.pathlist.PathViewList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webtype.LanguageType;
import by.kalilaska.ktattoo.webtype.TransitionType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by lovcov on 13.07.2017.
 */
public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private TransitionType transition;

    public SessionRequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        transition = TransitionType.FORWARD;
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public TransitionType getTransition() {
        return transition;
    }

	public void setTransition(TransitionType transition) {
		this.transition = transition;
	}

	public void extractValues(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        putValues(attributeNames, request);

        requestParameters.putAll(request.getParameterMap());

        HttpSession session = request.getSession();
        attributeNames = session.getAttributeNames();
        putValues(attributeNames, session);
    }

    private void putValues(Enumeration<String> attributeNames, HttpServletRequest request){
        String attrName;
        while (attributeNames.hasMoreElements()){
            attrName = attributeNames.nextElement();
            requestAttributes.put(attrName, request.getAttribute(attrName));
        }
    }

    private void putValues(Enumeration<String> attributeNames, HttpSession session){
        String attrName;
        while (attributeNames.hasMoreElements()){
            attrName = attributeNames.nextElement();
            sessionAttributes.put(attrName, session.getAttribute(attrName));
        }
    }

    public void insertRequestAttribute(String attrName, Object attrValue){
        requestAttributes.put(attrName, attrValue);
    }

    public void insertSessionAttribute(String attrName, Object attrValue){
        sessionAttributes.put(attrName, attrValue);
    }

    public void rewriteValues(HttpServletRequest request){

        Set<String> keySet = requestAttributes.keySet();

        for (String key : keySet) {
            request.setAttribute(key, requestAttributes.get(key));
        }

        keySet = sessionAttributes.keySet();
        HttpSession session = request.getSession();

        for (String key : keySet) {
            session.setAttribute(key, sessionAttributes.get(key));
        }   

    }
}
