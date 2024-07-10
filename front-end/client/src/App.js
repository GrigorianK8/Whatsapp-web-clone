import { Route, Routes } from 'react-router-dom'
import './App.css'
import HomePage from './components/HomePage'
import Status from './components/status/Status'
import StatusViewer from './components/status/StatusViewer'

function App() {
	return (
		<div className=''>
			<Routes>
				<Route path='/' element={<HomePage />}></Route>
				<Route path='/status' element={<Status />}></Route>
				<Route path='/status/:userId' element={<StatusViewer />}></Route>
			</Routes>
		</div>
	)
}

export default App
