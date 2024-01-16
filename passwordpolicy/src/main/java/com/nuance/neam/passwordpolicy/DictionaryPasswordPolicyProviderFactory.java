package com.nuance.neam.passwordpolicy;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PasswordPolicyProviderFactory;


public class DictionaryPasswordPolicyProviderFactory implements PasswordPolicyProviderFactory
{
   @Override
   public String getDisplayName()
   {
      return "Dictionary Password Policy";
   }

   @Override
   public String getConfigType()
   {
      return null;
   }

   @Override
   public String getDefaultConfigValue()
   {
      return null;
   }

   @Override
   public boolean isMultiplSupported()
   {
      return false;
   }

   @Override
   public PasswordPolicyProvider create(KeycloakSession keycloakSession)
   {
      return new DictionaryPasswordPolicyProvider(keycloakSession);
   }

   @Override
   public void init(Config.Scope scope)
   {

   }

   @Override
   public void postInit(KeycloakSessionFactory keycloakSessionFactory)
   {

   }

   @Override
   public void close()
   {

   }

   @Override
   public String getId()
   {
      return "dictionary-password-policy";
   }
}
