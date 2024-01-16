package com.nuance.neam.passwordpolicy;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PolicyError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DictionaryPasswordPolicyProvider implements PasswordPolicyProvider
{

   private static Logger LOGGER= LoggerFactory.getLogger(DictionaryPasswordPolicyProvider.class);
   private final KeycloakSession session;
   private Set<String> dictionary;

   public DictionaryPasswordPolicyProvider(KeycloakSession session)
   {
      this.session = session;
      dictionary=loadDictionaryFromFile("word_dictionary.txt");
   }

   private Set<String> loadDictionaryFromFile(String wordDictionary)
   {
      Set<String> loadedDictionary=new HashSet<>();
      InputStream inputStream= getClass().getClassLoader().getResourceAsStream(wordDictionary);
      if (inputStream!=null)
      {
         try(BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream))){

            String line;
            while ((line=reader.readLine())!=null)
            {
               loadedDictionary.add(line.trim().toLowerCase());
            }
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }
      return loadedDictionary;
   }

   @Override
   public PolicyError validate(RealmModel realmModel, UserModel userModel, String password)
   {
      return this.validate(userModel.getUsername(),password);
   }

   @Override
   public PolicyError validate(String username, String password)
   {
      List<String> dictWords=new ArrayList<>();
      for (String word: dictionary)
      {
            if(password.toLowerCase().contains(word.toLowerCase())){
               dictWords.add(word);
         }
      }
      if (dictWords.size()>0)
      {
         LOGGER.error("Dictionary Word Detected in the password :"+ dictWords);
         return new PolicyError("Password Rejected");
      }
      return null;
   }

   @Override
   public Object parseConfig(String value)
   {
      return null;
   }

   @Override
   public void close()
   {
   }

}
