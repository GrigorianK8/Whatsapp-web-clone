import { Button } from '@mui/material'
import Alert from '@mui/material/Alert'
import { green } from '@mui/material/colors'
import Snackbar from '@mui/material/Snackbar'
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { currentUser, register } from '../redux/auth/Action'

const Signup = () => {
	const [inputData, setInputData] = useState({
		fullName: '',
		email: '',
		password: '',
	})

	const navigate = useNavigate()
	const dispatch = useDispatch()
	const [openSnackbar, setOpenSnackbar] = useState(false)
	const { auth } = useSelector(store => store)
	const token = localStorage.getItem('token')

	const handleSubmit = e => {
		e.preventDefault()
		console.log('handle submit', inputData)
		dispatch(register(inputData))
		setOpenSnackbar(true)
	}

	const handleSnackbarClose = () => {
		setOpenSnackbar(false)
	}

	const handleChange = e => {
		const { name, value } = e.target
		setInputData(values => ({ ...values, [name]: value }))
	}

	useEffect(() => {
		if (token) dispatch(currentUser(token))
	}, [token])

	useEffect(() => {
		if (auth.userReq?.fullName) {
			navigate('/')
		}
	}, [auth.userReq])

	return (
		<div>
			<div className='flex justify-center h-screen items-center'>
				<div className='w-[30%] p-10 shadow-md bg-white'>
					<form onSubmit={handleSubmit} className='space-y-5'>
						<div>
							<p className='mb-2'>User Name</p>
							<input
								type='text'
								name='fullName'
								className='py-2 outline outline-green-600 w-full rounded-md border'
								placeholder='Enter username'
								onChange={handleChange}
								value={inputData.fullName}
							/>
						</div>
						<div>
							<p className='mb-2'>Email</p>
							<input
								type='text'
								name='email'
								className='py-2 outline outline-green-600 w-full rounded-md border'
								placeholder='Enter your email'
								onChange={handleChange}
								value={inputData.email}
							/>
						</div>
						<div>
							<p className='mb-2'>Password</p>
							<input
								type='password'
								name='password'
								className='py-2 outline outline-green-600 w-full rounded-md border'
								placeholder='Enter your password'
								onChange={handleChange}
								value={inputData.password}
							/>
						</div>
						<div>
							<Button
								type='submit'
								sx={{ bgcolor: green[700], padding: '.5rem 0rem' }}
								className='w-full'
								variant='contained'
							>
								Sign up
							</Button>
						</div>
					</form>
					<div className='flex space-x-3 items-center mt-5'>
						<p className=''>Already have an account?</p>
						<Button variant='text' onClick={() => navigate('/signin')}>
							signin
						</Button>
					</div>
				</div>
			</div>
			<Snackbar
				open={openSnackbar}
				autoHideDuration={6000}
				onClose={handleSnackbarClose}
			>
				<Alert
					onClose={handleSnackbarClose}
					severity='success'
					variant='filled'
					sx={{ width: '100%' }}
				>
					Your account successfully created !
				</Alert>
			</Snackbar>
		</div>
	)
}

export default Signup
