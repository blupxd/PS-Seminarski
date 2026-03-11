package controllers;

import dbb.DBBroker;
import operations.Operation;

public abstract class ApstraktnaGenerickaOperacija {

    protected DBBroker broker = DBBroker.getInstance();

    public abstract Operation execute(Operation op);

    public void zapocniTransakciju() throws Exception {
        broker.connect();
    }

    public void potvrdiTransakciju() throws Exception {
        broker.commit();
    }

    public void ponistiTransakciju() throws Exception {
        broker.rollback();
    }
}
