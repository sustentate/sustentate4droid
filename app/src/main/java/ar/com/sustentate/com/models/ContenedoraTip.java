package com.sustentate.app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emzas on 28/3/2018.
 */

public class ContenedoraTip {


    private List<Tip> listaTip = new ArrayList<>();

    public List<Tip> getListaTip() {
        return listaTip;
    }

    public ContenedoraTip(List<Tip> listaTip) {
        this.listaTip = listaTip;
    }

    @Override
    public String toString() {
        return "ContenedoraTip{" +
                "listaDeTips=" + listaTip +
                '}';
    }
}
