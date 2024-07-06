import React, { useState } from 'react'
import { AiOutlineSearch } from 'react-icons/ai'
import { BiCommentDetail } from 'react-icons/bi'
import { BsFilter, BsThreeDotsVertical } from 'react-icons/bs'
import { TbCircleDashed } from 'react-icons/tb'
import ChatCard from './chatCard/ChatCard'
import MessageCard from './messageCard/MessageCard'

const HomePage = () => {
	const [queries, setQueries] = useState(null)
	const [currentChat, setCurrentChat] = useState(null)

	const handleSearch = () => []
	const handleClickOnChatCard = () => [setCurrentChat(true)]

	return (
		<div className='relative'>
			<div className='w-full py-14 bg-[#00a884]'></div>
			<div className='flex bg-[#f0f2f5] h-[90vh] absolute left-[2vw] top-[5vh]  w-[96vw]'>
				<div className='left w-[30%] bg-[#e8e9ec] h-full'>
					<div className='flex justify-between items-center p-3'>
						<div className='flex items-center space-x-3'>
							<img
								className='rounded-full w-10 h-10 cursor'
								src='https://cdn.pixabay.com/photo/2020/05/09/13/29/photographer-5149664_1280.jpg'
								alt=''
							/>
							<p>username</p>
						</div>
						<div className='flex space-x-3 text-2xl'>
							<TbCircleDashed />
							<BiCommentDetail />
						</div>
					</div>
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
					<div className='bg-white overflow-y-scroll h-[76.8vh] px-3'>
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
						<div className='px-10 h-[85vh] overflow-y-scroll'>
							<div className='space-y-1 flex flex-col justify-center mt-20 py-2'>
								{[1, 1, 1, 1].map((item, i) => (
									<MessageCard
										isUserReqMessage={i % 2 === 0}
										content={'message'}
									/>
								))}
							</div>
						</div>
					</div>
				)}
			</div>
		</div>
	)
}

export default HomePage
