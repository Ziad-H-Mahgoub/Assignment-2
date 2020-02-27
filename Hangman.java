package eg.edu.alexu.csd.datastructure.hangman;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;



public class Hangman implements IHangman {
    private String[] dictionary ;
    private int maxWrong = 8;
    private String General;
    private char[] letterArray;
    final int noWordsRead = 340;//number of words to read from file


  public String[] readFile(String fileName)  {//function to read file to words array
      String[] words = new String [noWordsRead];
      FileReader fileReader;
      try {
          fileReader = new FileReader(fileName);
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          int i;
          for(i=0;i<noWordsRead;i++) {
              try {
                  words[i] = bufferedReader.readLine();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          try {
              bufferedReader.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      return words;

  }
    public void setDictionary(String[] words) {//will set dictionary  using read file function
        dictionary = words;

        letterArray = new char[selectRandomSecretWord().length()];
        Arrays.fill(letterArray, '_');
    }
        @Override
        public String selectRandomSecretWord () {
            int number;
            Random rand = new Random();
            number = rand.nextInt(340);
            String myword = dictionary[number];
            for (int i = 0; i < myword.length(); i++) {
                if (makeSureLettersFile(myword.charAt(i)) == '\0')
                    return null;
            }
            General = myword;
            return myword;
        }

        @Override
        public String guess (Character c) throws Exception {
            String testString = General;
            char test = makeSureLettersUser(c);
            char[] testArray;
            boolean found = false;
            int i = 0;
            testArray = wordArray(testString);

            if (maxWrong == 0)
                return null;
            else {
                if (test == 'S') {
                    setMaxWrongGuesses(--maxWrong);
                    return null;
                } else {
                    while (i < testArray.length) {
                        if (testArray[i] == test) {
                            letterArray[i] = testArray[i];
                            found = true;
                            break;
                        }
                        i++;
                    }
                    if (found) {
                        String outString = ArrayWord(letterArray);
                        return outString;
                    } else {
                        setMaxWrongGuesses(--maxWrong);
                        return null;
                    }
                }
            }
        }

        @Override
        public void setMaxWrongGuesses (Integer max){
            maxWrong = max;
        }

        //method to check char from file and make it lower case
        public char makeSureLettersFile ( char letter){
            if (letter <= 'z' && letter >= 'a')
                return letter;
            else if (letter >= 'A' && letter <= 'Z')
                return (char) (letter + 32);
            else if (letter == '-')
                return letter;
            return '\0';
        }

        //method to convert word string into array
        public char[] wordArray (String word){
            char[] myWordArray = new char[word.length()];
            for (int i = 0; i < word.length(); i++) {
                myWordArray[i] = makeSureLettersFile(word.charAt(i));
            }
            return myWordArray;
        }

        //method to convert word array into string
        public String ArrayWord ( char[] words){
            String convert = "";
            for (char word : words) {
                convert += word;
            }
            System.out.println(convert);
            return convert;
        }

        //method to check char from user
        public char makeSureLettersUser ( char user){
            if (user <= 'z' && user >= 'a')
                return user;
            else if (user >= 'A' && user <= 'Z')
                return (char) (user + 32);
            else if (user == '-')
                return user;
            else if (user == '\0')
                return '\0';
            return 'S';
        }
        //the game
        public void thegame () throws Exception {
            String sr;
            setDictionary(readFile("C:\\Users\\ZIAD\\IdeaProjects\\lab2\\game.txt"));
            setMaxWrongGuesses(8);
        while (true) {
                System.out.println("Enter the Character PLz");
                Scanner scanner = new Scanner(System.in);
                char ch = scanner.next().charAt(0);
                sr = guess(ch);
            System.out.println("Total Wrongs Left for you = "+maxWrong);
            if (maxWrong == 0) {
                        System.out.println("sorry you lose the game");
                        System.out.println("the word is " + General);
                        break;
                }

                else if (sr!=null){
                    if (sr.equals(General)){
                        System.out.println("you win the game");
                        System.out.println("the word is " + General);
                        break;
                    }


                }
            }

        }

    }

