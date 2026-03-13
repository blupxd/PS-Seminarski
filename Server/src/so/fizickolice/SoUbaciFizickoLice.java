package so.fizickolice;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.FizickoLiceDBB;
import models.FizickoLice;

public class SoUbaciFizickoLice extends ApstraktnaGenerickaOperacija {

    private final FizickoLiceDBB fizickoLiceDBB = new FizickoLiceDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof FizickoLice)) {
            throw new Exception("Parametar mora biti instanca klase FizickoLice.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        FizickoLice fl = (FizickoLice) param;
        fizickoLiceDBB.ubaciFizickoLice(fl);
        rezultat = fl;
    }
}
