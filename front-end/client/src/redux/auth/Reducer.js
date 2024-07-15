import {
	LOGIN,
	REGISTER,
	SEARCH_USER,
	UPDATE_USER,
	USER_REQ,
} from './ActionType'

const initialValue = {
	signup: null,
	signin: null,
}

export const authReducer = (store = initialValue, { type, payload }) => {
	if (type === REGISTER) {
		return { ...store, signup: payload }
	} else if (type === LOGIN) {
		return { ...store, signin: payload }
	} else if (type === USER_REQ) {
		return { ...store, userReq: payload }
	} else if (type === SEARCH_USER) {
		return { ...store, searchUser: payload }
	} else if (type === UPDATE_USER) {
		return { ...store, updateUser: payload }
	}
	return store
}
