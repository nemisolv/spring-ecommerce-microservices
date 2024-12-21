"use client"
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
  } from "@/components/ui/dialog"
  


interface ModalProps {
    title: string;
    description: string;
    isOpen: boolean;
    onClose: () => void;
    children?: React.ReactNode;
}

export const Modal: React.FC<ModalProps> = (
    { title, description, isOpen, onClose, children }
) => {

    const onChange = (open: boolean) => {
        if(!open) {
            onClose();
        }
    }

    return <Dialog open={isOpen} onOpenChange={onChange}>
    {/* <DialogTrigger>Open</DialogTrigger> */}
    <DialogContent>
      <DialogHeader>
        <DialogTitle>{title}</DialogTitle>
        <DialogDescription>
            {description}
        </DialogDescription>
      </DialogHeader>
      <div>
        {children}
      </div>
    </DialogContent>
  </Dialog>
  

}