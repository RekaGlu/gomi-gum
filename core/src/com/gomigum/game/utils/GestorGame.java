package com.gomigum.game.utils;

import com.gomigum.game.entities.GominolaEntity;

import java.util.ArrayList;

/**
 * Created by REKA on 11-Jun-16.
 */
public class GestorGame {

    /**
     * Busca una gominola en un array dado por medio de una id
     * @param gominolas array de gominolas
     * @param idBuscar id a buscar de la gominola
     * @return devuelve la gominola encontrada
     */
    public GominolaEntity devuelveGominolaPorID(ArrayList<GominolaEntity> gominolas, int idBuscar){
        for (int i = 0; i < gominolas.size(); i++) {
            if(gominolas.get(i).getId() == idBuscar){
                return gominolas.get(i) ;
            }
        }
        return null;
    }

}
