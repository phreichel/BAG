	.file	"Trie.cpp"
	.text
	.align 2
	.p2align 4
	.type	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE4findERS1_.isra.0, @function
_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE4findERS1_.isra.0:
.LFB2686:
	.cfi_startproc
	cmpq	$0, 24(%rdi)
	jne	.L2
	movq	16(%rdi), %rcx
	testq	%rcx, %rcx
	jne	.L4
	jmp	.L12
	.p2align 4,,10
	.p2align 3
.L19:
	movq	(%rcx), %rcx
	testq	%rcx, %rcx
	je	.L12
.L4:
	cmpb	%sil, 8(%rcx)
	jne	.L19
.L12:
	movq	%rcx, %rax
	ret
	.p2align 4,,10
	.p2align 3
.L2:
	movq	8(%rdi), %r9
	movsbq	%sil, %rax
	xorl	%edx, %edx
	divq	%r9
	movq	(%rdi), %rax
	movq	(%rax,%rdx,8), %rcx
	movq	%rdx, %r10
	testq	%rcx, %rcx
	je	.L12
	movq	(%rcx), %rax
	movzbl	8(%rax), %edi
	cmpb	%dil, %sil
	je	.L6
.L20:
	movq	(%rax), %r8
	testq	%r8, %r8
	je	.L8
	movzbl	8(%r8), %edi
	movq	%rax, %rcx
	xorl	%edx, %edx
	movsbq	%dil, %rax
	divq	%r9
	cmpq	%rdx, %r10
	jne	.L8
	movq	%r8, %rax
	cmpb	%dil, %sil
	jne	.L20
.L6:
	movq	(%rcx), %rcx
	movq	%rcx, %rax
	ret
.L8:
	xorl	%ecx, %ecx
	jmp	.L12
	.cfi_endproc
.LFE2686:
	.size	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE4findERS1_.isra.0, .-_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE4findERS1_.isra.0
	.section	.text._ZN3phi8TrieNodeD2Ev,"axG",@progbits,_ZN3phi8TrieNodeD5Ev,comdat
	.align 2
	.p2align 4
	.weak	_ZN3phi8TrieNodeD2Ev
	.type	_ZN3phi8TrieNodeD2Ev, @function
_ZN3phi8TrieNodeD2Ev:
.LFB1946:
	.cfi_startproc
	endbr64
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	movq	%rdi, %rbp
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	movq	16(%rdi), %rdi
	testq	%rdi, %rdi
	je	.L31
.L22:
	movq	8(%rbp), %rsi
	movsbq	8(%rdi), %rax
	xorl	%edx, %edx
	movq	0(%rbp), %r9
	movq	16(%rdi), %r12
	divq	%rsi
	leaq	(%r9,%rdx,8), %r10
	movq	%rdx, %r8
	movq	(%r10), %rdx
	movq	%rdx, %rax
	.p2align 4,,10
	.p2align 3
.L25:
	movq	%rax, %rcx
	movq	(%rax), %rax
	cmpq	%rdi, %rax
	jne	.L25
	movq	(%rdi), %rbx
	cmpq	%rcx, %rdx
	je	.L49
	testq	%rbx, %rbx
	je	.L28
	movsbq	8(%rbx), %rax
	xorl	%edx, %edx
	divq	%rsi
	cmpq	%rdx, %r8
	je	.L28
	movq	%rcx, (%r9,%rdx,8)
	movq	(%rdi), %rbx
.L28:
	movq	%rbx, (%rcx)
	movl	$24, %esi
	call	_ZdlPvm@PLT
	subq	$1, 24(%rbp)
	testq	%r12, %r12
	je	.L30
	movq	%r12, %rdi
	call	_ZN3phi8TrieNodeD1Ev
	movl	$64, %esi
	movq	%r12, %rdi
	call	_ZdlPvm@PLT
.L30:
	movq	(%rbx), %rdi
	testq	%rdi, %rdi
	jne	.L22
	movq	16(%rbp), %rbx
	testq	%rbx, %rbx
	je	.L31
	.p2align 4,,10
	.p2align 3
.L32:
	movq	%rbx, %rdi
	movq	(%rbx), %rbx
	movl	$24, %esi
	call	_ZdlPvm@PLT
	testq	%rbx, %rbx
	jne	.L32
.L31:
	movq	8(%rbp), %rax
	movq	0(%rbp), %rdi
	xorl	%esi, %esi
	leaq	0(,%rax,8), %rdx
	call	memset@PLT
	movq	0(%rbp), %rdi
	leaq	48(%rbp), %rax
	movq	$0, 24(%rbp)
	movq	$0, 16(%rbp)
	cmpq	%rax, %rdi
	je	.L50
	movq	8(%rbp), %rsi
	popq	%rbx
	.cfi_remember_state
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	salq	$3, %rsi
	jmp	_ZdlPvm@PLT
	.p2align 4,,10
	.p2align 3
.L49:
	.cfi_restore_state
	testq	%rbx, %rbx
	je	.L33
	movsbq	8(%rbx), %rax
	xorl	%edx, %edx
	divq	%rsi
	cmpq	%rdx, %r8
	je	.L28
	movq	%rcx, (%r9,%rdx,8)
	movq	(%r10), %rax
	leaq	16(%rbp), %rdx
	cmpq	%rdx, %rax
	je	.L51
.L29:
	movq	$0, (%r10)
	movq	(%rdi), %rbx
	jmp	.L28
	.p2align 4,,10
	.p2align 3
.L33:
	movq	%rcx, %rax
	leaq	16(%rbp), %rdx
	cmpq	%rdx, %rax
	jne	.L29
.L51:
	movq	%rbx, 16(%rbp)
	jmp	.L29
.L50:
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE1946:
	.size	_ZN3phi8TrieNodeD2Ev, .-_ZN3phi8TrieNodeD2Ev
	.weak	_ZN3phi8TrieNodeD1Ev
	.set	_ZN3phi8TrieNodeD1Ev,_ZN3phi8TrieNodeD2Ev
	.text
	.align 2
	.p2align 4
	.globl	_ZN3phi4TrieC2Ev
	.type	_ZN3phi4TrieC2Ev, @function
_ZN3phi4TrieC2Ev:
.LFB1973:
	.cfi_startproc
	endbr64
	leaq	48(%rdi), %rax
	movq	$1, 8(%rdi)
	movq	%rax, (%rdi)
	movq	$0, 16(%rdi)
	movq	$0, 24(%rdi)
	movl	$0x3f800000, 32(%rdi)
	movq	$0, 40(%rdi)
	movq	$0, 48(%rdi)
	movb	$0, 56(%rdi)
	ret
	.cfi_endproc
.LFE1973:
	.size	_ZN3phi4TrieC2Ev, .-_ZN3phi4TrieC2Ev
	.globl	_ZN3phi4TrieC1Ev
	.set	_ZN3phi4TrieC1Ev,_ZN3phi4TrieC2Ev
	.align 2
	.p2align 4
	.globl	_ZN3phi4TrieD2Ev
	.type	_ZN3phi4TrieD2Ev, @function
_ZN3phi4TrieD2Ev:
.LFB1976:
	.cfi_startproc
	endbr64
	jmp	_ZN3phi8TrieNodeD1Ev
	.cfi_endproc
.LFE1976:
	.size	_ZN3phi4TrieD2Ev, .-_ZN3phi4TrieD2Ev
	.globl	_ZN3phi4TrieD1Ev
	.set	_ZN3phi4TrieD1Ev,_ZN3phi4TrieD2Ev
	.align 2
	.p2align 4
	.globl	_ZN3phi4Trie3delERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE
	.type	_ZN3phi4Trie3delERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE, @function
_ZN3phi4Trie3delERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE:
.LFB1979:
	.cfi_startproc
	endbr64
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	movq	(%rsi), %r11
	movq	8(%rsi), %rbx
	addq	%r11, %rbx
	cmpq	%rbx, %r11
	jne	.L57
	jmp	.L55
	.p2align 4,,10
	.p2align 3
.L63:
	addq	$1, %r11
	movq	16(%rax), %rdi
	cmpq	%r11, %rbx
	je	.L55
.L57:
	movzbl	(%r11), %esi
	call	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE4findERS1_.isra.0
	testq	%rax, %rax
	jne	.L63
	popq	%rbx
	.cfi_remember_state
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L55:
	.cfi_restore_state
	movb	$0, 56(%rdi)
	popq	%rbx
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE1979:
	.size	_ZN3phi4Trie3delERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE, .-_ZN3phi4Trie3delERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE
	.align 2
	.p2align 4
	.globl	_ZNK3phi4Trie3hasERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE
	.type	_ZNK3phi4Trie3hasERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE, @function
_ZNK3phi4Trie3hasERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE:
.LFB1980:
	.cfi_startproc
	endbr64
	movq	%rsi, %rax
	movq	(%rsi), %rsi
	movq	8(%rax), %r11
	addq	%rsi, %r11
	cmpq	%r11, %rsi
	je	.L82
	.p2align 4,,10
	.p2align 3
.L81:
	cmpq	$0, 24(%rdi)
	movzbl	(%rsi), %ecx
	jne	.L90
	movq	16(%rdi), %rax
	testq	%rax, %rax
	jne	.L84
	jmp	.L85
	.p2align 4,,10
	.p2align 3
.L92:
	movq	(%rax), %rax
	testq	%rax, %rax
	je	.L91
.L84:
	cmpb	8(%rax), %cl
	jne	.L92
	addq	$1, %rsi
	movq	16(%rax), %rdi
	cmpq	%rsi, %r11
	jne	.L81
.L82:
	movzbl	56(%rdi), %eax
	ret
	.p2align 4,,10
	.p2align 3
.L90:
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
.L66:
	movq	8(%rdi), %r9
	movsbq	%cl, %rax
	xorl	%edx, %edx
	divq	%r9
	movq	(%rdi), %rax
	movq	(%rax,%rdx,8), %r10
	movq	%rdx, %rbx
	testq	%r10, %r10
	je	.L77
	movq	(%r10), %rax
	movzbl	8(%rax), %edi
	cmpb	%dil, %cl
	je	.L70
.L93:
	movq	(%rax), %r8
	testq	%r8, %r8
	je	.L77
	movzbl	8(%r8), %edi
	movq	%rax, %r10
	xorl	%edx, %edx
	movsbq	%dil, %rax
	divq	%r9
	cmpq	%rdx, %rbx
	jne	.L77
	movq	%r8, %rax
	cmpb	%dil, %cl
	jne	.L93
.L70:
	movq	(%r10), %rax
	testq	%rax, %rax
	je	.L77
.L68:
	addq	$1, %rsi
	movq	16(%rax), %rdi
	cmpq	%rsi, %r11
	je	.L94
	cmpq	$0, 24(%rdi)
	movzbl	(%rsi), %ecx
	jne	.L66
	movq	16(%rdi), %rax
	testq	%rax, %rax
	je	.L77
	.p2align 4,,10
	.p2align 3
.L69:
	cmpb	8(%rax), %cl
	je	.L68
	movq	(%rax), %rax
	testq	%rax, %rax
	jne	.L69
	popq	%rbx
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L91:
	.cfi_restore 3
	ret
	.p2align 4,,10
	.p2align 3
.L77:
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	xorl	%eax, %eax
	popq	%rbx
	.cfi_def_cfa_offset 8
	ret
.L85:
	.cfi_restore 3
	xorl	%eax, %eax
	ret
.L94:
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	movzbl	56(%rdi), %eax
	popq	%rbx
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE1980:
	.size	_ZNK3phi4Trie3hasERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE, .-_ZNK3phi4Trie3hasERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE
	.section	.text._ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm,"axG",@progbits,_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm,comdat
	.align 2
	.p2align 4
	.weak	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm
	.type	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm, @function
_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm:
.LFB2575:
	.cfi_startproc
	.cfi_personality 0x9b,DW.ref.__gxx_personality_v0
	.cfi_lsda 0x1b,.LLSDA2575
	endbr64
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	movq	%rdi, %rbp
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	movq	%rsi, %rbx
	subq	$8, %rsp
	.cfi_def_cfa_offset 48
	cmpq	$1, %rsi
	je	.L116
	movq	%rsi, %rax
	movq	%rdx, %r12
	shrq	$60, %rax
	jne	.L117
	leaq	0(,%rsi,8), %r13
	movq	%r13, %rdi
.LEHB0:
	call	_Znwm@PLT
	movq	%r13, %rdx
	xorl	%esi, %esi
	movq	%rax, %rdi
	movq	%rax, %r12
	call	memset@PLT
	leaq	48(%rbp), %r10
.L97:
	movq	16(%rbp), %rsi
	movq	$0, 16(%rbp)
	testq	%rsi, %rsi
	je	.L100
	xorl	%r8d, %r8d
	leaq	16(%rbp), %r9
	jmp	.L104
	.p2align 4,,10
	.p2align 3
.L101:
	movq	(%rdi), %rdx
	movq	%rdx, (%rcx)
	movq	(%rax), %rax
	movq	%rcx, (%rax)
	testq	%rsi, %rsi
	je	.L100
.L104:
	movq	%rsi, %rcx
	xorl	%edx, %edx
	movq	(%rsi), %rsi
	movsbq	8(%rcx), %rax
	divq	%rbx
	leaq	(%r12,%rdx,8), %rax
	movq	(%rax), %rdi
	testq	%rdi, %rdi
	jne	.L101
	movq	16(%rbp), %rdi
	movq	%rdi, (%rcx)
	movq	%rcx, 16(%rbp)
	movq	%r9, (%rax)
	cmpq	$0, (%rcx)
	je	.L102
	movq	%rcx, (%r12,%r8,8)
.L102:
	movq	%rdx, %r8
	testq	%rsi, %rsi
	jne	.L104
.L100:
	movq	0(%rbp), %rdi
	movq	8(%rbp), %rsi
	cmpq	%r10, %rdi
	je	.L105
	salq	$3, %rsi
	call	_ZdlPvm@PLT
.L105:
	movq	%rbx, 8(%rbp)
	movq	%r12, 0(%rbp)
	addq	$8, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 40
	popq	%rbx
	.cfi_def_cfa_offset 32
	popq	%rbp
	.cfi_def_cfa_offset 24
	popq	%r12
	.cfi_def_cfa_offset 16
	popq	%r13
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L116:
	.cfi_restore_state
	leaq	48(%rdi), %r10
	movq	$0, 48(%rdi)
	movq	%r10, %r12
	jmp	.L97
	.p2align 4,,10
	.p2align 3
.L117:
	shrq	$61, %rbx
	je	.L99
	call	_ZSt28__throw_bad_array_new_lengthv@PLT
.L99:
	call	_ZSt17__throw_bad_allocv@PLT
.LEHE0:
.L108:
	endbr64
	movq	%rax, %rdi
.L106:
	call	__cxa_begin_catch@PLT
	movq	(%r12), %rax
	movq	%rax, 40(%rbp)
.LEHB1:
	call	__cxa_rethrow@PLT
.LEHE1:
.L109:
	endbr64
	movq	%rax, %rbx
.L107:
	call	__cxa_end_catch@PLT
	movq	%rbx, %rdi
.LEHB2:
	call	_Unwind_Resume@PLT
.LEHE2:
	.cfi_endproc
.LFE2575:
	.globl	__gxx_personality_v0
	.section	.gcc_except_table._ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm,"aG",@progbits,_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm,comdat
	.align 4
.LLSDA2575:
	.byte	0xff
	.byte	0x9b
	.uleb128 .LLSDATT2575-.LLSDATTD2575
.LLSDATTD2575:
	.byte	0x1
	.uleb128 .LLSDACSE2575-.LLSDACSB2575
.LLSDACSB2575:
	.uleb128 .LEHB0-.LFB2575
	.uleb128 .LEHE0-.LEHB0
	.uleb128 .L108-.LFB2575
	.uleb128 0x1
	.uleb128 .LEHB1-.LFB2575
	.uleb128 .LEHE1-.LEHB1
	.uleb128 .L109-.LFB2575
	.uleb128 0
	.uleb128 .LEHB2-.LFB2575
	.uleb128 .LEHE2-.LEHB2
	.uleb128 0
	.uleb128 0
.LLSDACSE2575:
	.byte	0x1
	.byte	0
	.align 4
	.long	0

.LLSDATT2575:
	.section	.text._ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm,"axG",@progbits,_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm,comdat
	.size	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm, .-_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm
	.section	.text.unlikely,"ax",@progbits
	.align 2
.LCOLDB2:
	.text
.LHOTB2:
	.align 2
	.p2align 4
	.globl	_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE
	.type	_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE, @function
_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE:
.LFB1978:
	.cfi_startproc
	.cfi_personality 0x9b,DW.ref.__gxx_personality_v0
	.cfi_lsda 0x1b,.LLSDA1978
	endbr64
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	movq	%rdi, %rbp
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	subq	$40, %rsp
	.cfi_def_cfa_offset 96
	movq	(%rsi), %r11
	movq	%fs:40, %rax
	movq	%rax, 24(%rsp)
	xorl	%eax, %eax
	cmpq	$0, 8(%rsi)
	je	.L119
	movq	%rsi, %r13
	movq	%r11, %rbx
	jmp	.L132
	.p2align 4,,10
	.p2align 3
.L120:
	movq	16(%rax), %rbp
	movq	8(%r13), %rax
	addq	$1, %rbx
	addq	%r11, %rax
	cmpq	%rbx, %rax
	je	.L119
.L132:
	movzbl	(%rbx), %esi
	movq	%rbp, %rdi
	call	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE4findERS1_.isra.0
	testq	%rax, %rax
	jne	.L120
	movl	$64, %edi
.LEHB3:
	call	_Znwm@PLT
	pxor	%xmm0, %xmm0
	xorl	%edx, %edx
	movups	%xmm0, 16(%rax)
	movq	%rax, %r12
	leaq	48(%rax), %rax
	movups	%xmm0, -16(%rax)
	movups	%xmm0, (%rax)
	movq	%rax, (%r12)
	movq	$1, 8(%r12)
	movq	8(%rbp), %rdi
	movl	$0x3f800000, 32(%r12)
	movsbq	(%rbx), %r15
	movq	%r15, %rax
	movq	%r15, %r10
	divq	%rdi
	leaq	0(,%rdx,8), %rax
	movq	%rdx, %r9
	movq	%rax, 8(%rsp)
	movq	0(%rbp), %rax
	movq	(%rax,%rdx,8), %r8
	testq	%r8, %r8
	je	.L121
	movq	(%r8), %rax
	movzbl	8(%rax), %ecx
	cmpb	%cl, %r10b
	je	.L122
.L148:
	movq	(%rax), %rsi
	testq	%rsi, %rsi
	je	.L121
	movzbl	8(%rsi), %ecx
	movq	%rax, %r8
	xorl	%edx, %edx
	movsbq	%cl, %rax
	divq	%rdi
	cmpq	%rdx, %r9
	jne	.L121
	movq	%rsi, %rax
	cmpb	%cl, %r10b
	jne	.L148
.L122:
	movq	(%r8), %rax
	leaq	16(%rax), %r14
	testq	%rax, %rax
	je	.L121
.L124:
	movq	0(%r13), %r11
	movq	8(%r13), %rax
	addq	$1, %rbx
	movq	%r12, (%r14)
	movq	%r12, %rbp
	addq	%r11, %rax
	cmpq	%rbx, %rax
	jne	.L132
.L119:
	movb	$1, 56(%rbp)
	movq	24(%rsp), %rax
	subq	%fs:40, %rax
	jne	.L149
	addq	$40, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 56
	popq	%rbx
	.cfi_def_cfa_offset 48
	popq	%rbp
	.cfi_def_cfa_offset 40
	popq	%r12
	.cfi_def_cfa_offset 32
	popq	%r13
	.cfi_def_cfa_offset 24
	popq	%r14
	.cfi_def_cfa_offset 16
	popq	%r15
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L121:
	.cfi_restore_state
	movl	$24, %edi
	call	_Znwm@PLT
.LEHE3:
	leaq	32(%rbp), %rdi
	movl	$1, %ecx
	movq	$0, (%rax)
	movq	%rax, %r14
	movzbl	(%rbx), %eax
	movq	$0, 16(%r14)
	movb	%al, 8(%r14)
	movq	40(%rbp), %rax
	movq	24(%rbp), %rdx
	movq	8(%rbp), %rsi
	movq	%rax, 16(%rsp)
.LEHB4:
	call	_ZNKSt8__detail20_Prime_rehash_policy14_M_need_rehashEmmm@PLT
	movq	%rdx, %rsi
	testb	%al, %al
	jne	.L150
.L125:
	movq	0(%rbp), %rsi
	movq	8(%rsp), %rcx
	addq	%rsi, %rcx
	movq	(%rcx), %rax
	testq	%rax, %rax
	je	.L126
	movq	(%rax), %rax
	movq	%rax, (%r14)
	movq	(%rcx), %rax
	movq	%r14, (%rax)
.L127:
	addq	$1, 24(%rbp)
	addq	$16, %r14
	jmp	.L124
	.p2align 4,,10
	.p2align 3
.L150:
	leaq	16(%rsp), %rdx
	movq	%rbp, %rdi
	call	_ZNSt10_HashtableIcSt4pairIKcPN3phi8TrieNodeEESaIS5_ENSt8__detail10_Select1stESt8equal_toIcESt4hashIcENS7_18_Mod_range_hashingENS7_20_Default_ranged_hashENS7_20_Prime_rehash_policyENS7_17_Hashtable_traitsILb0ELb0ELb1EEEE9_M_rehashEmRKm
.LEHE4:
	movq	%r15, %rax
	xorl	%edx, %edx
	divq	8(%rbp)
	leaq	0(,%rdx,8), %rax
	movq	%rax, 8(%rsp)
	jmp	.L125
	.p2align 4,,10
	.p2align 3
.L126:
	movq	16(%rbp), %rax
	movq	%rax, (%r14)
	movq	%r14, 16(%rbp)
	movq	(%r14), %rax
	testq	%rax, %rax
	je	.L128
	movsbq	8(%rax), %rax
	xorl	%edx, %edx
	divq	8(%rbp)
	movq	%r14, (%rsi,%rdx,8)
.L128:
	leaq	16(%rbp), %rax
	movq	%rax, (%rcx)
	jmp	.L127
.L149:
	call	__stack_chk_fail@PLT
.L135:
	endbr64
	movq	%rax, %rbx
	jmp	.L130
	.section	.gcc_except_table,"a",@progbits
.LLSDA1978:
	.byte	0xff
	.byte	0xff
	.byte	0x1
	.uleb128 .LLSDACSE1978-.LLSDACSB1978
.LLSDACSB1978:
	.uleb128 .LEHB3-.LFB1978
	.uleb128 .LEHE3-.LEHB3
	.uleb128 0
	.uleb128 0
	.uleb128 .LEHB4-.LFB1978
	.uleb128 .LEHE4-.LEHB4
	.uleb128 .L135-.LFB1978
	.uleb128 0
.LLSDACSE1978:
	.text
	.cfi_endproc
	.section	.text.unlikely
	.cfi_startproc
	.cfi_personality 0x9b,DW.ref.__gxx_personality_v0
	.cfi_lsda 0x1b,.LLSDAC1978
	.type	_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE.cold, @function
_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE.cold:
.LFSB1978:
.L130:
	.cfi_def_cfa_offset 96
	.cfi_offset 3, -56
	.cfi_offset 6, -48
	.cfi_offset 12, -40
	.cfi_offset 13, -32
	.cfi_offset 14, -24
	.cfi_offset 15, -16
	movl	$24, %esi
	movq	%r14, %rdi
	call	_ZdlPvm@PLT
	movq	24(%rsp), %rax
	subq	%fs:40, %rax
	jne	.L151
	movq	%rbx, %rdi
.LEHB5:
	call	_Unwind_Resume@PLT
.LEHE5:
.L151:
	call	__stack_chk_fail@PLT
	.cfi_endproc
.LFE1978:
	.section	.gcc_except_table
.LLSDAC1978:
	.byte	0xff
	.byte	0xff
	.byte	0x1
	.uleb128 .LLSDACSEC1978-.LLSDACSBC1978
.LLSDACSBC1978:
	.uleb128 .LEHB5-.LCOLDB2
	.uleb128 .LEHE5-.LEHB5
	.uleb128 0
	.uleb128 0
.LLSDACSEC1978:
	.section	.text.unlikely
	.text
	.size	_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE, .-_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE
	.section	.text.unlikely
	.size	_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE.cold, .-_ZN3phi4Trie3addERKNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEE.cold
.LCOLDE2:
	.text
.LHOTE2:
	.hidden	DW.ref.__gxx_personality_v0
	.weak	DW.ref.__gxx_personality_v0
	.section	.data.rel.local.DW.ref.__gxx_personality_v0,"awG",@progbits,DW.ref.__gxx_personality_v0,comdat
	.align 8
	.type	DW.ref.__gxx_personality_v0, @object
	.size	DW.ref.__gxx_personality_v0, 8
DW.ref.__gxx_personality_v0:
	.quad	__gxx_personality_v0
	.ident	"GCC: (Ubuntu 13.2.0-23ubuntu4) 13.2.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	1f - 0f
	.long	4f - 1f
	.long	5
0:
	.string	"GNU"
1:
	.align 8
	.long	0xc0000002
	.long	3f - 2f
2:
	.long	0x3
3:
	.align 8
4:
