package flow.initialization;



import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import flow.entities.characters.*;
import flow.entities.characters.Character;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


public class JsonInput {
    public static ArrayList<Account> deserializeAccounts() {
        try {
            InputStream is = JsonInput.class.getResourceAsStream("accounts.json");

            if (is == null) {
                throw new IOException("Fișierul accounts.json nu a fost găsit!");
            }

            // Citim tot conținutul input stream-ului
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(content);
            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (int i=0; i < accountsArray.length(); i++) {
                JSONObject accountJson = (JSONObject) accountsArray.get(i);
                // name, country, games_number
                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String)accountJson.get("maps_completed"));

                // Credentials
                Credentials credentials = null;
                try {
                    JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");

                    credentials = new Credentials(email, password);
                } catch (JSONException e) {
                    System.out.println("! This account doesn't have all credentials !");
                }

                // Favorite games
                SortedSet<String> favoriteGames = new TreeSet();
                try {
                    JSONArray games = (JSONArray) accountJson.get("favorite_games");
                    for (int j = 0; j < games.length(); j++) {
                        favoriteGames.add((String) games.get(j));
                    }
                } catch (JSONException e) {
                    System.out.println("! This account doesn't have favorite games !");
                }

                // Characters
                ArrayList<Character> characters = new ArrayList<>();
                try {
                    JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                    for (int j = 0; j < charactersListJson.length(); j++) {
                        JSONObject charJson = (JSONObject) charactersListJson.get(j);
                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");
                        String level = (String) charJson.get("level");
                        int lvl = Integer.parseInt(level);
                        Integer experience = (Integer) charJson.get("experience");

                        Character newCharacter = null;

                        newCharacter = CharacterFactory.getCharacter(profession, cname, experience, lvl);

                        /*if (profession.equals("Warrior"))
                            newCharacter = new Warrior(cname, experience, lvl);
                        if (profession.equals("Rogue"))
                            newCharacter = new Rogue(cname, experience, lvl);
                        if (profession.equals("Mage"))
                            newCharacter = new Mage(cname, experience, lvl);*/

                        characters.add(newCharacter);

                    }
                } catch (JSONException e) {
                    System.out.println("! This account doesn't have characters !");
                }
                /*Account account = new Account(characters, gamesNumber, null);*/
               /* Account.Information information = account.new Information(credentials, favoriteGames, name, country);
                account = new Account(characters, gamesNumber, information);*/
                Account.Information information = new Account.Information.InformationBuilder()
                        .setCredJucator(credentials)
                        .setListaJocuri(favoriteGames)
                        .setNumeJucator(name)
                        .setTaraJucator(country)
                        .build();
                Account account = new Account(characters, gamesNumber, information);
                accounts.add(account);
            }
            return accounts;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}