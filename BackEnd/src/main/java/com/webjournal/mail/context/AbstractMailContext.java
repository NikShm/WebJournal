package com.webjournal.mail.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AbstractMailContext
 * @since 11/1/2022 - 21.56
 **/
public abstract class AbstractMailContext {
    private String from;
    private String to;
    private String subject;
    private String templateLocation;
    private Map<String, Object> context;

    public AbstractMailContext() {
        context = new HashMap<>();
    }

    public <T> void init(T context) {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public void put(String key, Object value) {
        if (key != null) {
            context.put(key.intern(), value);
        }
    }
}
