package bll.validators;

import model.Client;

/**
 * Validator pentru validarea vârstei unui client.
 */
public class ClientAgeValidator implements Validator<Client> {

	/** Vârsta minimă acceptată pentru un client. */
	private static final int MIN_AGE = 18;

	/**
	 * Validează vârsta unui client.
	 * @param client Clientul de validat.
	 * @throws IllegalArgumentException Dacă vârsta clientului este mai mică decât limita minimă.
	 */
	public void validate(Client client) {
		if (client.getAge() < MIN_AGE) {
			throw new IllegalArgumentException("The Client Age limit is not respected!");
		}
	}
}
