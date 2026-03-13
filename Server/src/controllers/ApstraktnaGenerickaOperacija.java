package controllers;

import dbb.DBBroker;

public abstract class ApstraktnaGenerickaOperacija {

    protected DBBroker broker = DBBroker.getInstance();
    protected Object rezultat;

    public final void izvrsi(Object param) throws Exception {
        try {
            zapocniTransakciju();
            proveriPreduslove(param);
            izvrsiOperaciju(param);
            potvrdiTransakciju();
        } catch (Exception e) {
            ponistiTransakciju();
            throw e;
        }
    }

    protected abstract void proveriPreduslove(Object param) throws Exception;
    protected abstract void izvrsiOperaciju(Object param) throws Exception;

    public Object getRezultat() {
        return rezultat;
    }

    private void zapocniTransakciju() throws Exception {
        broker.beginTransaction();
    }

    private void potvrdiTransakciju() throws Exception {
        broker.commit();
    }

    private void ponistiTransakciju() throws Exception {
        broker.rollback();
    }
}
