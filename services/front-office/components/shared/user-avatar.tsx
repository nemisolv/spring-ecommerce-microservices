import { Avatar,  AvatarImage } from "@/components/ui/avatar"
import { cn } from "@/lib/utils";

interface UserAvatarProps {
    src?: string;
    className?: string;
}


export const UserAvatar = ({src, className}: UserAvatarProps) => {
    return <Avatar className={cn(
        "size-7 md:size-10", className
    )}>
        <AvatarImage src={src} />
    </Avatar>

}