import { BASE_URL } from '../../config/api'
import {
	LOGIN,
	LOGOUT,
	REGISTER,
	SEARCH_USER,
	UPDATE_USER,
	USER_REQ,
} from './ActionType'

export const register = data => async dispatch => {
	try {
		const res = await fetch(`${BASE_URL}/auth/signup`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data),
		})
		const resData = await res.json()
		if (resData.jwt) localStorage.setItem('token', resData.jwt)
		console.log('register ', resData)
		dispatch({ type: REGISTER, payload: resData })
	} catch (error) {}
}

export const login = data => async dispatch => {
	try {
		const res = await fetch(`${BASE_URL}/auth/sign-in`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data),
		})
		const resData = await res.json()
		if (resData.jwt) localStorage.setItem('token', resData.jwt)
		console.log('login ', resData)
		dispatch({ type: LOGIN, payload: resData })
	} catch (error) {
		console.log('catch error ', error)
	}
}

export const currentUser = token => async dispatch => {
	try {
		const res = await fetch(`${BASE_URL}/api/users/profile`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${token}`,
			},
		})
		const resData = await res.json()
		console.log('current user ', resData)
		dispatch({ type: USER_REQ, payload: resData })
	} catch (error) {
		console.log('catch error ', error)
	}
}

export const searchUser = data => async dispatch => {
	try {
		const res = await fetch(
			`${BASE_URL}/api/users/search?name=${data.keyword}`,
			{
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${data.token}`,
				},
			}
		)
		const resData = await res.json()
		console.log('register ', resData)
		dispatch({ type: SEARCH_USER, payload: resData })
	} catch (error) {
		console.log('catch error ', error)
	}
}

export const updateUser = data => async dispatch => {
	try {
		const res = await fetch(`${BASE_URL}/api/users/update/${data.id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${data.token}`,
			},
		})
		const resData = await res.json()
		console.log('register ', resData)
		dispatch({ type: UPDATE_USER, payload: resData })
	} catch (error) {
		console.log('catch error ', error)
	}
}

export const logout = () => async dispatch => {
	localStorage.removeItem('token')
	dispatch({ type: LOGOUT, payload: null })
	dispatch({ type: USER_REQ, payload: null })
}
