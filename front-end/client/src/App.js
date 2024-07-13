import { Route, Routes } from 'react-router-dom'
import './App.css'
import HomePage from './components/HomePage'
import Status from './components/status/Status'
import StatusViewer from './components/status/StatusViewer'
import Signin from './register/Signin'
import Signup from './register/Signup'

function App() {
	return (
		<div className=''>
			<Routes>
				<Route path='/' element={<HomePage />}></Route>
				<Route path='/status' element={<Status />}></Route>
				<Route path='/status/:userId' element={<StatusViewer />}></Route>
				<Route path='/signin' element={<Signin />}></Route>
				<Route path='/signup' element={<Signup />}></Route>
			</Routes>
		</div>
	)
}

export default App
