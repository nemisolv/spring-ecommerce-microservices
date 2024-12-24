import {create} from 'zustand'


interface useStoreModalStoreProps {
    isOpen: boolean;
    onOpen: () => void;
    onClose: () => void;
}



export const useStoreModal = create<useStoreModalStoreProps>((set) => ({

    isOpen: false,
    onOpen: () => set({isOpen: true}),
    onClose: () => set({isOpen: false}) 

}))