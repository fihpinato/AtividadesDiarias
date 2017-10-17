package br.com.filipepinato.atividadesdiarias.dao;

import com.activeandroid.query.Select;

import java.util.List;

import br.com.filipepinato.atividadesdiarias.model.Atividade;

public class AtividadeDAO {
    public List<Atividade> findAll(){
        return new Select()
                .from(Atividade.class)
                .orderBy("descricao ASC")
                .execute();
    }
}
