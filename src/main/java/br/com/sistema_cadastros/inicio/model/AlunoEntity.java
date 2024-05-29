package br.com.sistema_cadastros.inicio.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import org.springframework.cglib.reflect.MethodDelegate.Generator;

import br.com.sistema_cadastros.inicio.Enum.Genero;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
@Table(name = "Alunos")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class AlunoEntity {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
 // @NotNull
 // @NotBlank(message = "Esse campo não pode ser vazio")
   private String nome;

  // @NotBlank(message = "Esse campo não pode ser vazio")
  // @CPF
  // @NotNull
   private String cpf;

///  @NotNull
 //  @NotBlank(message = "Esse campo não pode ser vazio")
   private int matricula;

 //  @NotNull
  // @NotBlank(message = "Esse campo não pode ser vazio")
   private Genero genero;

 // @NotNull
   //@NotBlank(message = "Esse campo não pode ser vazio")
   private String curso;

   //@NotNull
  /// @NotBlank(message = "Esse campo não pode ser vazio")
   private  String data;


   
   
   
    private Boolean ativo = true;

}