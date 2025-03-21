package com.whatsapp.integretion.Services;


import java.util.List;

public class WhatsappBusinessAccount {
    private String object;
    private List<Entry> entry;

    // Getters and Setters
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public static class Entry {
        private String id;
        private List<Change> changes;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Change> getChanges() {
            return changes;
        }

        public void setChanges(List<Change> changes) {
            this.changes = changes;
        }

        public static class Change {
            private Value value;
            private String field;

            // Getters and Setters
            public Value getValue() {
                return value;
            }

            public void setValue(Value value) {
                this.value = value;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public static class Value {
                private String messagingProduct;
                private Metadata metadata;
                private List<Contact> contacts;
                private List<Message> messages;

                // Getters and Setters
                public String getMessagingProduct() {
                    return messagingProduct;
                }

                public void setMessagingProduct(String messagingProduct) {
                    this.messagingProduct = messagingProduct;
                }

                public Metadata getMetadata() {
                    return metadata;
                }

                public void setMetadata(Metadata metadata) {
                    this.metadata = metadata;
                }

                public List<Contact> getContacts() {
                    return contacts;
                }

                public void setContacts(List<Contact> contacts) {
                    this.contacts = contacts;
                }

                public List<Message> getMessages() {
                    return messages;
                }

                public void setMessages(List<Message> messages) {
                    this.messages = messages;
                }

                public static class Metadata {
                    private String displayPhoneNumber;
                    private String phoneNumberId;

                    // Getters and Setters
                    public String getDisplayPhoneNumber() {
                        return displayPhoneNumber;
                    }

                    public void setDisplayPhoneNumber(String displayPhoneNumber) {
                        this.displayPhoneNumber = displayPhoneNumber;
                    }

                    public String getPhoneNumberId() {
                        return phoneNumberId;
                    }

                    public void setPhoneNumberId(String phoneNumberId) {
                        this.phoneNumberId = phoneNumberId;
                    }
                }

                public static class Contact {
                    private Profile profile;
                    private String waId;

                    // Getters and Setters
                    public Profile getProfile() {
                        return profile;
                    }

                    public void setProfile(Profile profile) {
                        this.profile = profile;
                    }

                    public String getWaId() {
                        return waId;
                    }

                    public void setWaId(String waId) {
                        this.waId = waId;
                    }

                    public static class Profile {
                        private String name;

                        // Getters and Setters
                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }
                    }
                }

                public static class Message {
                    private String from;
                    private String id;
                    private String timestamp;
                    private Text text;
                    private String type;

                    // Getters and Setters
                    public String getFrom() {
                        return from;
                    }

                    public void setFrom(String from) {
                        this.from = from;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getTimestamp() {
                        return timestamp;
                    }

                    public void setTimestamp(String timestamp) {
                        this.timestamp = timestamp;
                    }

                    public Text getText() {
                        return text;
                    }

                    public void setText(Text text) {
                        this.text = text;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public static class Text {
                        private String body;

                        // Getters and Setters
                        public String getBody() {
                            return body;
                        }

                        public void setBody(String body) {
                            this.body = body;
                        }
                    }
                }
            }
        }
    }
}
