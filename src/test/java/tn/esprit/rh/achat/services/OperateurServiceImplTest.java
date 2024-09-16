package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Operateur;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class OperateurServiceImplTest {
    @Autowired
    IOperateurService operateurService ;

    @Test
    @Order(1)
    void retrieveAllOperateurs() {
        List<Operateur> allOperateur = operateurService.retrieveAllOperateurs();
        assertEquals(allOperateur.size(),allOperateur.size());
    }

    @Test
    @Order(2)
    void addOperateur() {
        Operateur op = operateurService.addOperateur(
                Operateur.builder()
                        .nom("test")
                        .password("test")
                        .prenom("test")
                        .build()
        );
        Assertions.assertNotNull(op);
    }

    @Test
    @Order(3)
    void deleteOperateur() {
        Operateur op = operateurService.addOperateur(
                Operateur.builder()
                        .nom("test")
                        .password("test")
                        .prenom("test")
                        .build()
        );
        operateurService.deleteOperateur(op.getIdOperateur());
        Assertions.assertNull(operateurService.retrieveOperateur(op.getIdOperateur()));
    }

    @Test
    @Order(4)
    void updateOperateur() {
        Operateur op = operateurService.addOperateur(
                Operateur.builder()
                        .nom("test")
                        .password("test")
                        .prenom("test")
                        .build()
        );
        op.setNom("Wiem");
        operateurService.updateOperateur(op);
        Assertions.assertEquals("Wiem",operateurService.updateOperateur(op).getNom());

    }

    @Test
    @Order(5)
    void retrieveOperateur() {
        Operateur op = operateurService.addOperateur(
                Operateur.builder()
                        .nom("test")
                        .password("test")
                        .prenom("test")
                        .build()
        );
        Assertions.assertEquals(op.getIdOperateur(), operateurService.retrieveOperateur(op.getIdOperateur()).getIdOperateur() );
    }


}