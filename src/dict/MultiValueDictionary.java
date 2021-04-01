package dict;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MultiValueDictionary {

	public static final String INVALID_COMMAND = "ERROR, Invalid command.";
	public static final String INVALID_COMMAND_PARAMETER = "ERROR, Invalid command parameter.";
	public static final String INVALID_MEMBER = "ERROR, key does not exist.";
	public static final String INVALID_VALUE_REMOVAL = "ERROR, value does not exist";
	public static final String INVALID_KEY_REMOVAL = "ERROR, key does not exist";
	public static final String DUPLICATE_VALUE = "ERROR, value already exists";
	public static final String ADDED = "Added";
	public static final String REMOVED = "Removed";
	public static final String CLEARED = "Cleared";

	public static final String EMPTY_SET = "empty set";
	public static final String PREFIX = ") ";
	public static final String COLON = ": ";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static Scanner scan;
	public static Map<String, Set<String>> dictionary;

	public static void main(String[] args) {

		init();
		boolean continueProcessing = true;
		while (continueProcessing) {
			String input = scan.nextLine();

			if (isNullEmptyOrBlank(input)) {
				System.out.println(INVALID_COMMAND);
			}

			String[] inputArray = input.split("\\s+");
			switch (inputArray[0].toUpperCase()) {
			case "ADD":
				add(inputArray[1], inputArray[2]);
				break;
			case "KEYS":
				listKeys();
				break;
			case "MEMBERS":
				listMembers(inputArray[1]);
				break;
			case "REMOVE":
				remove(inputArray);
				break;
			case "REMOVEALL":
				removeAll(inputArray[1]);
				break;
			case "CLEAR":
				clear();
				break;
			case "KEYEXISTS":
				keyExists(inputArray[1]);
				break;
			case "VALUEEXISTS":
				valueExists(inputArray[1], inputArray[2]);
				break;
			case "ALLMEMBERS":
				listValues();
				break;
			case "ITEMS":
				listAll();
				break;
			default:
				System.out.println(PREFIX + INVALID_COMMAND);
				break;
			}
		}

	}

	public static void init() {
		scan = new Scanner(System.in);
		dictionary = new HashMap<String, Set<String>>();
	}

	public static boolean isNullEmptyOrBlank(String... args) {
		for (String currentString : args) {
			if (currentString == null || currentString.isEmpty() || currentString.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static void add(String key, String value) {

		if (isNullEmptyOrBlank(key, value)) {
			System.out.println(PREFIX + INVALID_COMMAND_PARAMETER);
			return;
		}

		if (dictionary.containsKey(key)) {
			Set<String> set = dictionary.get(key);
			if (set.contains(value)) {
				System.out.println(PREFIX + DUPLICATE_VALUE);
				return;
			}
			set.add(value);
			dictionary.put(key, set);
		} else {
			Set<String> set = new HashSet<String>();
			set.add(value);
			dictionary.put(key, set);
		}
		System.out.println(PREFIX + ADDED);
	}

	public static void listKeys() {
		if (dictionary.keySet().size() == 0) {
			System.out.println(PREFIX + EMPTY_SET);
		}

		int i = 1;
		for (String currentString : dictionary.keySet()) {
			System.out.println(i + PREFIX + currentString);
			i++;
		}
	}

	public static void listMembers(String key) {
		if (!dictionary.containsKey(key)) {
			System.out.println(PREFIX + INVALID_MEMBER);
			return;
		}

		int i = 1;
		for (String currentString : dictionary.get(key)) {
			System.out.println(i + PREFIX + currentString);
			i++;
		}
	}

	public static void remove(String[] input) {
		if (input.length == 2) {
			if (dictionary.containsKey(input[1])) {
				dictionary.remove(input[1]);
			} else {
				System.out.println(PREFIX + INVALID_KEY_REMOVAL);
			}
			return;
		}

		String key = input[1];
		String value = input[2];

		if (dictionary.containsKey(key)) {
			Set<String> set = dictionary.get(key);
			if (set.contains(value)) {
				set.remove(value);
				if (set.size() == 0) {
					dictionary.remove(key);
				} else {
					dictionary.put(key, set);
				}
				System.out.println(PREFIX + REMOVED);

			} else {
				System.out.println(PREFIX + INVALID_VALUE_REMOVAL);
			}
		} else {
			System.out.println(PREFIX + INVALID_KEY_REMOVAL);
		}
	}

	public static void removeAll(String key) {
		if (dictionary.containsKey(key)) {
			dictionary.remove(key);
			System.out.println(PREFIX + REMOVED);
		} else {
			System.out.println(PREFIX + INVALID_KEY_REMOVAL);
		}
	}

	public static void clear() {
		dictionary = new HashMap<String, Set<String>>();
		System.out.println(PREFIX + CLEARED);
	}

	public static void keyExists(String key) {
		if (dictionary.containsKey(key)) {
			System.out.println(PREFIX + TRUE);
		} else {
			System.out.println(PREFIX + FALSE);
		}
	}

	public static void valueExists(String key, String value) {
		if (dictionary.containsKey(key) && dictionary.get(key).contains(value)) {
			System.out.println(PREFIX + TRUE);
		} else {
			System.out.println(PREFIX + FALSE);
		}
	}

	public static void listValues() {
		if (dictionary.size() == 0) {
			System.out.println(PREFIX + EMPTY_SET);
			return;
		}
		int i = 1;
		for (String key : dictionary.keySet()) {
			for (String value : dictionary.get(key)) {
				System.out.println(i + PREFIX + value);
				i++;
			}
		}
	}

	public static void listAll() {
		if (dictionary.size() == 0) {
			System.out.println(PREFIX + EMPTY_SET);
			return;
		}
		int i = 1;
		for (String key : dictionary.keySet()) {
			for (String value : dictionary.get(key)) {
				System.out.println(i + PREFIX + key + COLON + value);
				i++;
			}
		}
	}
}
