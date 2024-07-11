import React, { useState } from 'react'
import { BsArrowLeft } from 'react-icons/bs'
import SelectedMember from './SelectedMember'

const CreateGroup = () => {
	const [newGroup, setNewGroup] = useState(false)
	const [groupMember, setGroupMember] = useState(new Set())
	const [query, setQuery] = useState('')

	const handleRemoveMember = item => {
		groupMember.delete(item)
		setGroupMember(groupMember)
	}

	const handleSearch = () => {}

	return (
		<div className='w-full h-full'>
			{!newGroup && (
				<div>
					<div className='flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5'>
						<BsArrowLeft className='cursor-pointer text-2xl font-bold' />
						<p className='text-xl font-semibold'>Add Group Participants</p>
					</div>
					<div className='relative bg-white py-4 px-3'>
						<div className='flex space-x-2 flex-wrap space-y-1'>
							{groupMember.size > 0 &&
								Array.from(groupMember).map(item => (
									<SelectedMember
										handleRemoveMember={() => handleRemoveMember(item)}
										member={item}
									/>
								))}
						</div>
						<input
							type='text'
							onChange={e => {
								handleSearch(e.target.value)
								setQuery(e.target.value)
							}}
							className='outline-none border-b border-[#8888] p-2 w-[93%]'
							placeholder='Search user'
							value={query}
						/>
					</div>
				</div>
			)}
		</div>
	)
}

export default CreateGroup
