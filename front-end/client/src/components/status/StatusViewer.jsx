import React, { useEffect, useState } from 'react'
import { AiOutlineClose } from 'react-icons/ai'
import { BsArrowLeft } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'
import ProgressBarr from './ProgressBar'
import { stories } from './story'

const StatusViewer = () => {
	const [currentStoryIndex, setCurrentStoryIndex] = useState(0)
	const [activeIndex, setActiveIndex] = useState(0)
	const navigate = useNavigate()

	const handleNextStory = () => {
		if (currentStoryIndex < stories?.length - 1) {
			setCurrentStoryIndex(currentStoryIndex + 1)
			setActiveIndex(activeIndex + 1)
		} else {
			setCurrentStoryIndex(0)
			setActiveIndex(0)
		}
	}

	useEffect(() => {
		const intervalId = setInterval(() => {
			handleNextStory()
		}, 2000)
		return () => clearInterval(intervalId)
	}, [currentStoryIndex])

	const handleNavigate = () => {
		navigate(-1)
	}

	return (
		<div>
			<div className='relative flex justify-center items-center h-[100vh] bg-slate-900'>
				<div className='relative'>
					<img
						className='max-h-[96vh] object-contain'
						src={stories?.[currentStoryIndex].image}
						alt=''
					/>
					<div className='absolute top-0 flex w-full'>
						{stories.map((item, index) => (
							<ProgressBarr
								key={index}
								duration={2000}
								index={index}
								activeIndex={activeIndex}
							/>
						))}
					</div>
				</div>
				<div>
					<BsArrowLeft
						onClick={handleNavigate}
						className='absolute top-10 left-10 text-2xl text-white cursor-pointer'
					/>
					<AiOutlineClose
						onClick={handleNavigate}
						className='absolute top-10 right-10 text-2xl text-white cursor-pointer'
					/>
				</div>
			</div>
		</div>
	)
}

export default StatusViewer
