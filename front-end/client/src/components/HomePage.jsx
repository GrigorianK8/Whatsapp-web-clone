import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import React, { useEffect, useState } from 'react'
import { AiOutlineSearch } from 'react-icons/ai'
import { BiCommentDetail } from 'react-icons/bi'
import {
	BsEmojiSmile,
	BsFilter,
	BsMicFill,
	BsThreeDotsVertical,
} from 'react-icons/bs'
import { ImAttachment } from 'react-icons/im'
import { TbCircleDashed } from 'react-icons/tb'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { currentUser, logout } from '../redux/auth/Action'
import ChatCard from './chatCard/ChatCard'
import CreateGroup from './group/CreateGroup'
import './HomePage.css'
import MessageCard from './messageCard/MessageCard'
import Profile from './profile/Profile'

const HomePage = () => {
	const [queries, setQueries] = useState(null)
	const [currentChat, setCurrentChat] = useState(null)
	const [content, setContent] = useState('')
	const [isProfile, setIsProfile] = useState(false)
	const navigate = useNavigate()
	const [isGroup, setIsGroup] = useState(false)
	const dispatch = useDispatch()
	const { auth } = useSelector(store => store)
	const token = localStorage.getItem('token')

	const handleSearch = () => {}
	const handleClickOnChatCard = () => [setCurrentChat(true)]
	const handleCreateNewMessage = () => {}
	const handleNavigate = () => {
		setIsProfile(true)
	}

	const handleCloseOpenProfile = () => {
		setIsProfile(false)
	}

	const [anchorEl, setAnchorEl] = useState(null)
	const open = Boolean(anchorEl)
	const handleClick = e => {
		setAnchorEl(e.currentTarget)
	}
	const handleClose = () => {
		setAnchorEl(null)
	}

	const handleCreateGroup = () => {
		setIsGroup(true)
	}

	const handleLogout = () => {
		dispatch(logout())
		navigate('/signup')
	}

	useEffect(() => {
		if (!auth.userReq) {
			navigate('/signup')
		}
	}, [auth.userReq])

	useEffect(() => {
		dispatch(currentUser('token'))
	}, [token])

	return (
		<div className='relative'>
			<div className='w-full py-14 bg-[#00a884]'></div>
			<div className='flex bg-[#f0f2f5] h-[90vh] absolute left-[2vw] top-[5vh]  w-[96vw]'>
				<div className='left w-[30%] bg-[#e8e9ec] h-full'>
					{/*PROFILE*/}
					{isGroup && <CreateGroup />}
					{isProfile && (
						<Profile handleCloseOpenProfile={handleCloseOpenProfile} />
					)}
					{!isProfile && !isGroup && (
						<div className='w-full'>
							{/*HOME*/}
							{
								<div className='flex justify-between items-center p-3'>
									<div
										onClick={handleNavigate}
										className='flex items-center space-x-3'
									>
										<img
											className='rounded-full w-10 h-10 cursor-pointer'
											src='https://cdn.pixabay.com/photo/2020/05/09/13/29/photographer-5149664_1280.jpg'
											alt=''
										/>
										<p>{auth.userReq?.fullName}</p>
									</div>
									<div className='flex space-x-3 text-2xl'>
										<TbCircleDashed
											className='cursor-pointer'
											onClick={() => navigate('/status')}
										/>
										<BiCommentDetail />
										<div>
											<BsThreeDotsVertical
												className='cursor-pointer'
												id='basic-button'
												aria-controls={open ? 'basic-menu' : undefined}
												aria-haspopup='true'
												aria-expanded={open ? 'true' : undefined}
												onClick={handleClick}
											/>
											<Menu
												id='basic-menu'
												anchorEl={anchorEl}
												open={open}
												onClose={handleClose}
												MenuListProps={{
													'aria-labelledby': 'basic-button',
												}}
											>
												<MenuItem onClick={handleClose}>Profile</MenuItem>
												<MenuItem onClick={handleCreateGroup}>
													Create group
												</MenuItem>
												<MenuItem onClick={handleLogout}>Logout</MenuItem>
											</Menu>
										</div>
									</div>
								</div>
							}
							<div className='relative flex justify-center items-center bg-white py-4 px-3'>
								<input
									className='border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-2'
									type='text'
									placeholder='Search or start new chat'
									onChange={e => {
										setQueries(e.target.value)
										handleSearch(e.target.value)
									}}
									value={queries}
								/>
								<AiOutlineSearch className='left-5 top-7 absolute' />
								<div>
									<BsFilter className='ml-4 text-3xl' />
								</div>
							</div>
							{/*ALL USERS*/}
							<div className='bg-white overflow-y-scroll h-[72vh] px-3'>
								{queries &&
									[1, 1, 1, 1].map(item => (
										<div onClick={handleClickOnChatCard}>
											{''}
											<hr />
											<ChatCard />
											{''}
										</div>
									))}
							</div>
						</div>
					)}
				</div>
				{/*DEFAULT PAGE*/}
				{!currentChat && (
					<div className='w-[70%] flex flex-col items-center justify-center h-full'>
						<div className='max-w-[70%] text-center'>
							<img className='mx-auto' src='/img/whatsapp.png' alt='whatsapp' />
							<h1 className='text-4xl my-5 text-gray-600'>WhatsApp Web</h1>
							<p className='my-7'>
								Send and review message without keeping your phone online. Use
								WhatsApp on up to 4 linked devices and 1 phone at the same time.
							</p>
						</div>
					</div>
				)}
				{/*MESSAGE PART*/}
				{currentChat && (
					<div className='w-[70%] relative'>
						<div className='header absolute top-0 w-full bg-[#f0f2f5]'>
							<div className='flex justify-between'>
								<div className='py-3 space-x-4 flex items-center px-3'>
									<img
										className='w-10 h-10 rounded-full'
										src='https://cdn.pixabay.com/photo/2019/12/11/21/27/portrait-4689365_1280.jpg'
										alt=''
									/>
									<p>username</p>
								</div>
								<div className='py-3 flex space-x-4 items-center px-3'>
									<AiOutlineSearch />
									<BsThreeDotsVertical />
								</div>
							</div>
						</div>
						{/*MESSAGE BODY*/}
						<div className='px-10 h-[85vh] overflow-y-scroll bg-blue-200'>
							<div className='space-y-1 flex flex-col justify-center mt-20 py-2'>
								{[1, 1, 1, 1].map((item, i) => (
									<MessageCard
										isUserReqMessage={i % 2 === 0}
										content={'message'}
									/>
								))}
							</div>
						</div>
						{/*FOOTER PART*/}
						<div className='footer bg-[#f0f2f5] absolute bottom-0 w-full py-3 text-2xl'>
							<div className='flex justify-between items-center px-5 relative'>
								<BsEmojiSmile className='cursor-pointer' />
								<ImAttachment />

								<input
									className='py-2 outline-none border-none bg-white pl-4 rounded-md w-[85%]'
									type='text'
									onChange={e => setContent(e.target.value)}
									placeholder='Type Message'
									value={content}
									onKeyPress={e => {
										if (e.key === 'Enter') {
											handleCreateNewMessage()
											setContent('')
										}
									}}
								/>
								<BsMicFill />
							</div>
						</div>
					</div>
				)}
			</div>
		</div>
	)
}

export default HomePage
